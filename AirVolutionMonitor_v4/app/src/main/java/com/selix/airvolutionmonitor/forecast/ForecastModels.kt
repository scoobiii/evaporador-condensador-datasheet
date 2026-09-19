package com.selix.airvolutionmonitor.forecast
data class ForecastPoint(val ds:String,val yhat:Double,val lower:Double,val upper:Double)
data class ForecastResult(val metric:String,val horizonHours:Int,val points:List<ForecastPoint>)
