"""Adaptação das leituras Midea para o contrato mínimo HVACLY."""

from dataclasses import asdict, dataclass
from datetime import datetime, timezone
from math import isfinite
from typing import Any


@dataclass(frozen=True)
class HvaclyMeasurement:
    schema_version: str
    asset_id: str
    node_id: str
    sensor_id: str
    measured_at: str
    received_at: str
    quantity: str
    value: float
    unit: str
    quality: str
    source: str


def _measurement(asset_id: str, node_id: str, sensor_id: str, quantity: str,
                 value: Any, unit: str, timestamp_ms: int) -> HvaclyMeasurement | None:
    if value is None or isinstance(value, bool):
        return None
    try:
        number = float(value)
    except (TypeError, ValueError):
        return None
    if not isfinite(number):
        return None
    measured = datetime.fromtimestamp(timestamp_ms / 1000, tz=timezone.utc)
    received = datetime.now(timezone.utc)
    return HvaclyMeasurement(
        schema_version="1.0",
        asset_id=asset_id,
        node_id=node_id,
        sensor_id=sensor_id,
        measured_at=measured.isoformat().replace("+00:00", "Z"),
        received_at=received.isoformat().replace("+00:00", "Z"),
        quantity=quantity,
        value=number,
        unit=unit,
        quality="CONFIRMED",
        source="midea-local",
    )


def to_hvacly_events(telemetry: dict[str, Any], asset_id: str, node_id: str) -> list[dict[str, Any]]:
    timestamp_ms = int(telemetry.get("timestamp_ms") or 0)
    channels = {
        "t1_c": ("sensor-t1", "temperature", "degC"),
        "t2_c": ("sensor-t2", "temperature", "degC"),
        "t3_c": ("sensor-t3", "temperature", "degC"),
        "t4_c": ("sensor-t4", "temperature", "degC"),
        "setpoint_c": ("setpoint", "temperature_setpoint", "degC"),
    }
    events = []
    for field, (sensor_id, quantity, unit) in channels.items():
        item = _measurement(asset_id, node_id, sensor_id, quantity, telemetry.get(field), unit, timestamp_ms)
        if item:
            events.append(asdict(item))
    return events
