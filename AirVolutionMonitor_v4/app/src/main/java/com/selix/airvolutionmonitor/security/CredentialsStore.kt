package com.selix.airvolutionmonitor.security

import android.content.Context
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class CredentialsStore(context: Context) {
    private val prefs = context.getSharedPreferences("airvolution_secure", Context.MODE_PRIVATE)
    private val keyAlias = "airvolution_gateway_key"

    fun save(gatewayUrl: String, ip: String, token: String, key: String, beaconMac: String, rssiThreshold: Int) {
        val payload = listOf(gatewayUrl, ip, token, key, beaconMac, rssiThreshold.toString()).joinToString("\u001f")
        prefs.edit().putString("payload", encrypt(payload)).apply()
    }

    fun load(): Credentials? {
        val raw = prefs.getString("payload", null) ?: return null
        return runCatching {
            val values = decrypt(raw).split("\u001f")
            if (values.size != 6) return null
            Credentials(values[0], values[1], values[2], values[3], values[4], values[5].toIntOrNull() ?: -75)
        }.getOrNull()
    }

    fun clear() = prefs.edit().clear().apply()

    private fun secretKey(): SecretKey {
        val ks = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        (ks.getKey(keyAlias, null) as? SecretKey)?.let { return it }
        return KeyGenerator.getInstance("AES", "AndroidKeyStore").apply {
            init(android.security.keystore.KeyGenParameterSpec.Builder(keyAlias,
                android.security.keystore.KeyProperties.PURPOSE_ENCRYPT or android.security.keystore.KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE).build())
        }.generateKey()
    }

    private fun encrypt(value: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply { init(Cipher.ENCRYPT_MODE, secretKey()) }
        return Base64.encodeToString(cipher.iv, Base64.NO_WRAP) + ":" +
            Base64.encodeToString(cipher.doFinal(value.toByteArray(StandardCharsets.UTF_8)), Base64.NO_WRAP)
    }

    private fun decrypt(value: String): String {
        val parts = value.split(":", limit = 2)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, secretKey(), GCMParameterSpec(128, Base64.decode(parts[0], Base64.NO_WRAP)))
        return String(cipher.doFinal(Base64.decode(parts[1], Base64.NO_WRAP)), StandardCharsets.UTF_8)
    }
}

data class Credentials(val gatewayUrl: String, val ip: String, val token: String, val key: String, val beaconMac: String, val rssiThreshold: Int)
