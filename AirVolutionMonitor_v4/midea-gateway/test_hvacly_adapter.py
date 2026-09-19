from hvacly_adapter import to_hvacly_events


def test_exports_identity_and_units():
    events = to_hvacly_events({"t1_c": 23.4, "t2_c": None, "timestamp_ms": 0}, "asset-1", "node-1")
    assert len(events) == 1
    assert events[0]["asset_id"] == "asset-1"
    assert events[0]["node_id"] == "node-1"
    assert events[0]["sensor_id"] == "sensor-t1"
    assert events[0]["unit"] == "degC"
    assert events[0]["quality"] == "CONFIRMED"
    assert events[0]["source"] == "midea-local"


def test_drops_non_numeric_values():
    assert to_hvacly_events({"t1_c": "unknown", "timestamp_ms": 0}, "a", "n") == []
