package com.github.amanbutnot.bridge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.amanbutnot.bridge.connectivity.BridgeServiceConnector
import com.github.amanbutnot.bridge.discovery.BridgeServiceDiscovery
import com.github.amanbutnot.bridge.discovery.DiscoveredBridgeService
import com.github.amanbutnot.bridge.ui.theme.BridgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BridgeTheme {
                BridgeConsoleApp(packageManager = packageManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BridgeConsoleApp(packageManager: android.content.pm.PackageManager) {
    val context = LocalContext.current
    val discovery = remember { BridgeServiceDiscovery(packageManager) }

    var services by remember { mutableStateOf<List<DiscoveredBridgeService>>(emptyList()) }
    var hasScanned by remember { mutableStateOf(false) }
    val connectedMap = remember { mutableStateMapOf<String, Boolean>() }

    fun scan() {
        services = discovery.discoverServices()
        hasScanned = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bridge Console", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = { scan() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Scan Services")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (services.isEmpty()) {
                Text(
                    text = if (hasScanned) "No packages found" else "Tap scan to search for services",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(services, key = { it.packageName + it.serviceClassName }) { service ->
                        val isConnected = connectedMap[service.packageName] ?: false

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = service.packageName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = service.serviceClassName,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                if (isConnected) {
                                    OutlinedButton(
                                        onClick = {
                                            val connector = BridgeServiceConnector(service, context)
                                            connectedMap[service.packageName] = connector.connect()
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Connected")
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            val connector = BridgeServiceConnector(service, context)
                                            val accepted = connector.connect()
                                            connectedMap[service.packageName] = accepted
                                            Log.d("BridgeConsole", "Bound $accepted")
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("Connect")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
