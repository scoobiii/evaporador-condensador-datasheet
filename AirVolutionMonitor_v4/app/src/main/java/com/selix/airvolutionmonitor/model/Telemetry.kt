package com.selix.airvolutionmonitor.model
enum class ValueOrigin { OBSERVED, FORECAST, DERIVED, MANUAL, UNKNOWN }
data class Telemetry(val timestampMs:Long,val deviceId:String,val metric:String,val value:Double?,val unit:String,val origin:ValueOrigin=ValueOrigin.OBSERVED,val quality:String="unknown",val rawAttribute:String?=null)
