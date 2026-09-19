package com.selix.airvolutionmonitor.midea
import com.selix.airvolutionmonitor.model.Telemetry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
interface MideaLanClient { suspend fun discover(subnet:String="192.168.1"):List<MideaDevice>; suspend fun connect(device:MideaDevice):Boolean; fun telemetry():Flow<Telemetry>; suspend fun close() }
data class MideaDevice(val ip:String,val port:Int=6444,val deviceId:String?=null,val model:String?=null)
class MideaProtocolAdapter: MideaLanClient { override suspend fun discover(subnet:String)=emptyList<MideaDevice>(); override suspend fun connect(device:MideaDevice)=false; override fun telemetry():Flow<Telemetry>=flow{}; override suspend fun close(){} }
