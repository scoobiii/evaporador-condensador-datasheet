package com.selix.airvolutionmonitor.midea

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject

data class GatewayTelemetry(
    val power: Boolean?, val mode: String?, val setpointC: Double?, val t1C: Double?,
    val t2C: Double?, val t3C: Double?, val fan: String?, val error: String?, val timestampMs: Long
)

class MideaGatewayClient(private val baseUrl: String, private val http: OkHttpClient = OkHttpClient()) {
    suspend fun connect(ip: String, token: String, key: String): String = withContext(Dispatchers.IO) {
        val payload = JSONObject().put("ip", ip).put("token", token).put("key", key).toString()
        val request = Request.Builder().url(url("/connect")).post(payload.toRequestBody("application/json".toMediaType())).build()
        http.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            if (!response.isSuccessful) error("Falha de autenticação (${response.code}): $body")
            JSONObject(body).optJSONObject("device")?.optString("model").orEmpty()
        }
    }

    suspend fun status(): GatewayTelemetry = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(url("/status")).get().build()
        http.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            if (!response.isSuccessful) error("Gateway HTTP ${response.code}: $body")
            val t = JSONObject(body).getJSONObject("telemetry")
            GatewayTelemetry(
                power = if (t.isNull("power")) null else t.optBoolean("power"),
                mode = t.optString("mode").takeIf { it.isNotBlank() },
                setpointC = number(t, "setpoint_c"), t1C = number(t, "t1_c"),
                t2C = number(t, "t2_c"), t3C = number(t, "t3_c"),
                fan = t.optString("fan").takeIf { it.isNotBlank() },
                error = t.optString("error").takeIf { it.isNotBlank() },
                timestampMs = t.optLong("timestamp_ms", System.currentTimeMillis())
            )
        }
    }

    private fun url(path: String) = baseUrl.trimEnd('/') + path
    private fun number(json: JSONObject, key: String): Double? = if (json.isNull(key)) null else json.optDouble(key).takeUnless { it.isNaN() }
}
