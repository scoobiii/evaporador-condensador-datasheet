# AirVolution Monitor v3

Midea AirVolution -> M-Smart/LAN -> validated decoder -> normalized Telemetry -> time-series storage -> dashboard -> Prophet.

## Provenance
OBSERVED = device data; FORECAST = Prophet; DERIVED = calculated; MANUAL = user input; UNKNOWN = unverified.

## Acquisition boundary
`MideaLanClient` isolates the protocol. This skeleton intentionally does not invent Midea packet bytes or cryptographic keys. Connect it to a validated Midea-LAN implementation for the target device.

## Forecast
Prophet service supports 24h and 30d, returning yhat and an 80% interval. Forecast is always visually distinct from observed measurements.
