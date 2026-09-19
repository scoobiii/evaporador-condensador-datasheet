package com.selix.airvolutionmonitor

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.selix.airvolutionmonitor.midea.GatewayTelemetry
import com.selix.airvolutionmonitor.midea.MideaGatewayClient
import com.selix.airvolutionmonitor.proximity.ProximityMonitor
import com.selix.airvolutionmonitor.security.Credentials
import com.selix.airvolutionmonitor.security.CredentialsStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { App() } }
}

@Composable
fun App() {
    val context = LocalContext.current
    val store = remember { CredentialsStore(context) }
    var credentials by remember { mutableStateOf(store.load()) }
    var tab by remember { mutableIntStateOf(0) }
    var data by remember { mutableStateOf<GatewayTelemetry?>(null) }
    var proximity by remember { mutableStateOf("Aproxime-se da máquina") }
    var connection by remember { mutableStateOf("Aguardando proximidade") }
    var rssi by remember { mutableStateOf<Int?>(null) }
    var permissionGranted by remember { mutableStateOf(hasBlePermission(context)) }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        permissionGranted = result.values.all { it }
    }
    val monitor = remember { ProximityMonitor(context) }
    val scope = rememberCoroutineScope()
    var telemetryJob by remember { mutableStateOf<Job?>(null) }

    DisposableEffect(credentials, permissionGranted) {
        if (credentials != null && permissionGranted) {
            val started = monitor.start(credentials!!.beaconMac, credentials!!.rssiThreshold) { near, signal ->
                rssi = signal
                scope.launch {
                    if (near) {
                        proximity = "MÁQUINA PRÓXIMA"
                        if (telemetryJob?.isActive == true) return@launch
                        connection = "Autenticando…"
                        telemetryJob = scope.launch {
                            runCatching {
                            val c = credentials!!
                            val client = MideaGatewayClient(c.gatewayUrl)
                            client.connect(c.ip, c.token, c.key)
                            connection = "ONLINE • dados em tempo real"
                            while (true) { data = client.status(); delay(3_000) }
                            }.onFailure { connection = "ERRO • ${it.message?.take(90) ?: "gateway indisponível"}" }
                        }
                    } else {
                        proximity = "Máquina fora do alcance"
                        connection = "Sessão encerrada"
                        telemetryJob?.cancel()
                        telemetryJob = null
                        data = null
                    }
                }
            }
            if (!started) connection = "Bluetooth desligado ou permissão pendente"
        }
        onDispose { monitor.stop() }
    }

    Scaffold(bottomBar = { NavigationBar {
        listOf("Monitor", "Configurar", "Diagnóstico").forEachIndexed { i, label ->
            NavigationBarItem(selected = tab == i, onClick = { tab = i }, icon = { Text(listOf("◉", "⚙", "!")[i]) }, label = { Text(label, fontSize = 11.sp) })
        }
    } }) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
            when (tab) {
                0 -> MonitorScreen(proximity, connection, rssi, data)
                1 -> SetupScreen(credentials, permissionGranted, onPermission = {
                    if (Build.VERSION.SDK_INT >= 31) permissionLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT))
                }, onSave = { value -> store.save(value.gatewayUrl, value.ip, value.token, value.key, value.beaconMac, value.rssiThreshold); credentials = value; tab = 0 })
                else -> Diagnostics()
            }
        }
    }
}

@Composable
private fun MonitorScreen(proximity: String, connection: String, rssi: Int?, data: GatewayTelemetry?) {
    Text("AirVolution Monitor", style = MaterialTheme.typography.headlineSmall)
    Text("Presença física → autenticação segura → leitura real", fontSize = 12.sp)
    Spacer(Modifier.height(16.dp))
    Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)) {
        Text(proximity, style = MaterialTheme.typography.titleMedium)
        Text(connection, color = if (connection.startsWith("ONLINE")) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
        rssi?.let { Text("Sinal BLE: ${it} dBm", fontSize = 12.sp) }
    } }
    Spacer(Modifier.height(12.dp))
    val rows = listOf("T1 / ambiente" to data?.t1C?.let { "%.1f °C".format(it) }, "T2 / evaporador" to data?.t2C?.let { "%.1f °C".format(it) }, "T3 / condensador" to data?.t3C?.let { "%.1f °C".format(it) }, "Estado" to data?.power?.let { if (it) "LIGADO" else "DESLIGADO" }, "Setpoint" to data?.setpointC?.let { "%.1f °C".format(it) }, "Modo" to data?.mode, "Fan" to data?.fan, "Erro" to data?.error)
    rows.forEach { (name, value) -> ListItem(headlineContent = { Text(name) }, trailingContent = { Text(value ?: "—") }) }
    Text("A telemetria é exibida somente após autenticação no gateway local. O app não envia comandos para a máquina.", fontSize = 11.sp)
}

@Composable
private fun SetupScreen(current: Credentials?, permissionGranted: Boolean, onPermission: () -> Unit, onSave: (Credentials) -> Unit) {
    var gateway by remember(current) { mutableStateOf(current?.gatewayUrl ?: "http://192.168.1.100:8000") }
    var ip by remember(current) { mutableStateOf(current?.ip ?: "192.168.1.50") }
    var token by remember(current) { mutableStateOf(current?.token ?: "") }
    var key by remember(current) { mutableStateOf(current?.key ?: "") }
    var mac by remember(current) { mutableStateOf(current?.beaconMac ?: "AA:BB:CC:DD:EE:FF") }
    var threshold by remember(current) { mutableStateOf((current?.rssiThreshold ?: -75).toString()) }
    Text("Configuração", style = MaterialTheme.typography.headlineSmall)
    Text("Use um beacon BLE instalado próximo à evaporadora. O MAC identifica a máquina.", fontSize = 12.sp)
    Spacer(Modifier.height(10.dp))
    listOf("Gateway URL" to gateway, "IP da máquina" to ip, "Token" to token, "Key" to key, "MAC do beacon BLE" to mac, "RSSI mínimo (dBm)" to threshold).forEach { (label, value) ->
        OutlinedTextField(value = value, onValueChange = { new -> when (label) { "Gateway URL" -> gateway = new; "IP da máquina" -> ip; "Token" -> token; "Key" -> key; "MAC do beacon BLE" -> mac; else -> threshold = new } }, label = { Text(label) }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(6.dp))
    }
    if (!permissionGranted) Button(onClick = onPermission) { Text("Permitir proximidade Bluetooth") }
    Spacer(Modifier.height(8.dp))
    Button(onClick = { onSave(Credentials(gateway.trim(), ip.trim(), token, key, mac.trim().uppercase(), threshold.toIntOrNull() ?: -75)) }, enabled = token.isNotBlank() && key.isNotBlank() && mac.contains(":"), modifier = Modifier.fillMaxWidth()) { Text("Salvar e ativar monitoramento") }
    Text("Token e Key ficam cifrados no Android Keystore; não são enviados para fora da sua rede.", fontSize = 11.sp)
}

@Composable private fun Diagnostics() { Text("Diagnóstico", style = MaterialTheme.typography.headlineSmall); Spacer(Modifier.height(8.dp)); listOf("E4 / sensor ambiente", "E5 / sensor evaporador", "F2 / sensor condensador", "EC / proteção relacionada a refrigerante", "status_code / código de estado").forEach { ListItem(headlineContent = { Text(it) }) }; Text("Os códigos exibidos são diagnósticos; não substituem a documentação do modelo.", fontSize = 11.sp) }

private fun hasBlePermission(context: android.content.Context): Boolean = Build.VERSION.SDK_INT < 31 || ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
