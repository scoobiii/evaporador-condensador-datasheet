package com.selix.airvolutionmonitor.data
import com.selix.airvolutionmonitor.model.Telemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
class TelemetryStore { private val _items=MutableStateFlow<List<Telemetry>>(emptyList()); val items:StateFlow<List<Telemetry>>=_items.asStateFlow(); fun append(x:Telemetry){_items.value=(_items.value+x).takeLast(10000)} }
