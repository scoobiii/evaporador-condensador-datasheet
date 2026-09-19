package com.selix.airvolutionmonitor.proximity

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class ProximityMonitor(private val context: Context) {
    private val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
    private val adapter: BluetoothAdapter? get() = bluetoothManager?.adapter
    private var scanner: BluetoothLeScanner? = null
    private var callback: ScanCallback? = null
    private var targetMac: String = ""
    private var threshold = -75
    private var nearHits = 0
    private var absentHits = 0

    fun start(mac: String, rssiThreshold: Int, onPresenceChanged: (Boolean, Int?) -> Unit): Boolean {
        if (!hasPermission() || adapter?.isEnabled != true) return false
        targetMac = mac.trim().uppercase()
        threshold = rssiThreshold
        nearHits = 0
        absentHits = 0
        scanner = adapter?.bluetoothLeScanner
        callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                if (result.device.address.uppercase() != targetMac) return
                val near = result.rssi >= threshold
                if (near) { nearHits++; absentHits = 0 } else { absentHits++; nearHits = 0 }
                when {
                    nearHits >= 2 -> onPresenceChanged(true, result.rssi)
                    absentHits >= 4 -> onPresenceChanged(false, result.rssi)
                }
            }
            override fun onScanFailed(errorCode: Int) = onPresenceChanged(false, null)
        }
        scanner?.startScan(callback)
        return true
    }

    fun stop() {
        if (hasPermission()) callback?.let { scanner?.stopScan(it) }
        callback = null
        scanner = null
    }

    private fun hasPermission(): Boolean =
        android.os.Build.VERSION.SDK_INT < 31 || ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
}
