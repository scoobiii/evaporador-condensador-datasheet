from datetime import datetime
import pandas as pd
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from prophet import Prophet
app=FastAPI(title="AirVolution Forecast")
class Point(BaseModel): ds:datetime; y:float
class Req(BaseModel): metric:str; history:list[Point]; horizon_hours:int=24
@app.get('/health')
def health(): return {'ok':True,'engine':'prophet'}
@app.post('/forecast')
def forecast(r:Req):
 if r.horizon_hours not in (24,720): raise HTTPException(400,'horizon_hours must be 24 or 720')
 if len(r.history)<10: raise HTTPException(400,'at least 10 points required')
 df=pd.DataFrame([p.model_dump() for p in r.history]).sort_values('ds')
 m=Prophet(daily_seasonality=True,weekly_seasonality=True,yearly_seasonality=len(df)>=2160,interval_width=.80);m.fit(df)
 d=df.ds.diff().dropna().dt.total_seconds(); step=int(d.median()) if len(d) else 1800; n=max(1,int(r.horizon_hours*3600/step)); fc=m.predict(m.make_future_dataframe(periods=n,freq=pd.Timedelta(seconds=step)))
 return {'metric':r.metric,'horizon_hours':r.horizon_hours,'forecast':[{'ds':x.ds.isoformat(),'yhat':float(x.yhat),'lower':float(x.yhat_lower),'upper':float(x.yhat_upper)} for x in fc.tail(n).itertuples()]}
