# Midea Gateway

This is the real-protocol adapter boundary.

It uses the maintained `midea-local` package (12.0.0) rather than inventing
Midea packet bytes. The gateway runs on a machine that can reach the AC on
the same LAN and exposes a small HTTP API to the Android app.

## 1. Install

Python 3.12+:

```bash
python -m venv .venv
# activate it
pip install -r requirements.txt
uvicorn main:app --host 0.0.0.0 --port 8000
```

## 2. Discover

```text
GET http://GATEWAY_IP:8000/discover?ip=192.168.1.X
```

The maintained library supports discovery by IP and the standard M-Smart
port 6444.

## 3. Connect

POST `/connect`:

```json
{
  "ip": "192.168.1.50",
  "port": 6444,
  "token": "YOUR_TOKEN",
  "key": "YOUR_KEY"
}
```

For v3 devices, the token/key pair is required for local authentication.
Do not commit these values.

## 4. Read the first telemetry packet

```text
GET /status
```

The response normalizes:

- power
- mode
- setpoint
- T1 / indoor ambient
- T2 / indoor coil
- T3 / outdoor coil
- T4 when exposed
- fan
- error/status
- raw attributes

The maintained Midea LAN documentation exposes T1/T2/T3 and other AC
entities when the device capability supports them. A device may not expose
every field.

## HVACLY export

`GET /telemetry/hvacly` exposes the observed values as versioned HVACLY
events. Each event includes `asset_id`, `node_id`, `sensor_id`, quantity,
unit, measured/received timestamps, quality and source. The Android `/status`
flow remains unchanged. Set `HVACLY_ASSET_ID` and `HVACLY_NODE_ID` in the
environment when the default identifiers are not appropriate.

## Security

Run this gateway on a trusted LAN only. Do not expose port 8000 to the
internet. Keep token/key out of source control.
