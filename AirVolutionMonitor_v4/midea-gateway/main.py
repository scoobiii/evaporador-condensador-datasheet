
import os, asyncio, time
from typing import Any
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from midealocal.discover import discover
from midealocal.devices import device_selector

app = FastAPI(title="AirVolution Midea LAN Gateway", version="1.0")

class ConnectRequest(BaseModel):
    ip: str
    token: str
    key: str
    port: int = 6444
    device_id: int | None = None
    device_type: int | None = None
    protocol: int = 3
    model: str | None = None
    subtype: int = 0

class Gateway:
    ac = None
    meta = {}

gateway = Gateway()

def pick(d: dict, *names):
    for n in names:
        if n in d and d[n] is not None:
            return d[n]
    return None

def normalize(attrs: dict) -> dict:
    # Attribute names changed in the maintained library; accept both
    # current semantic names and older aliases.
    return {
        "power": pick(attrs, "power_on", "powerOn"),
        "mode": pick(attrs, "mode"),
        "setpoint_c": pick(attrs, "target_temperature", "temperature_setpoint",
                           "temperatureSetpoint", "target_temperature_c"),
        "t1_c": pick(attrs, "indoor_ambient_temperature", "indoor_temperature",
                     "indoorTemperature"),
        "t2_c": pick(attrs, "indoor_coil_temperature"),
        "t3_c": pick(attrs, "outdoor_coil_temperature"),
        "t4_c": pick(attrs, "outdoor_ambient_temperature", "outdoor_temperature"),
        "fan": pick(attrs, "fan_speed", "fanSpeed"),
        "error": pick(attrs, "status_code", "statusCode", "in_error", "inError"),
        "raw": attrs,
        "timestamp_ms": int(time.time()*1000)
    }

@app.get("/health")
def health():
    return {"ok": True, "connected": gateway.ac is not None}

@app.get("/discover")
def do_discover(ip: str | None = None):
    try:
        found = discover(ip_address=ip) if ip else discover()
        return {"devices": list(found.values())}
    except Exception as e:
        raise HTTPException(502, f"discovery failed: {e}")

@app.post("/connect")
def connect(req: ConnectRequest):
    try:
        device = None
        if req.device_id is not None and req.device_type is not None:
            device = {
                "device_id": req.device_id,
                "type": req.device_type,
                "ip_address": req.ip,
                "port": req.port,
                "protocol": req.protocol,
                "model": req.model or "",
            }
        else:
            found = discover(ip_address=req.ip)
            if not found:
                raise RuntimeError("No Midea device discovered at that IP")
            device = next(iter(found.values()))
        gateway.ac = device_selector(
            name="AirVolution",
            device_id=device["device_id"],
            device_type=device["type"],
            ip_address=device["ip_address"],
            port=device.get("port", req.port),
            token=req.token,
            key=req.key,
            device_protocol=device.get("protocol", req.protocol),
            model=device.get("model", req.model or ""),
            subtype=req.subtype,
            customize="",
        )
        gateway.ac.connect()
        gateway.meta = device
        return {"ok": True, "device": device}
    except Exception as e:
        gateway.ac = None
        raise HTTPException(502, f"connect/authentication failed: {e}")

@app.get("/status")
def status():
    if gateway.ac is None:
        raise HTTPException(409, "not connected")
    try:
        # The maintained library exposes normalized attributes after connect.
        attrs = dict(gateway.ac.attributes or {})
        return {"ok": True, "telemetry": normalize(attrs)}
    except Exception as e:
        raise HTTPException(502, f"status read failed: {e}")

@app.get("/raw")
def raw():
    if gateway.ac is None:
        raise HTTPException(409, "not connected")
    return {"attributes": dict(gateway.ac.attributes or {})}
