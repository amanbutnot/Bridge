package com.github.amanbutnot.bridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.amanbutnot.bridge.discovery.BridgeServiceDiscovery
import com.github.amanbutnot.bridge.discovery.DiscoveredBridgeService
import com.github.amanbutnot.bridge.ui.theme.BridgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BridgeTheme {
                val disService = BridgeServiceDiscovery(packageManager)
                var packageNames by remember {
                    mutableStateOf<List<DiscoveredBridgeService>>(
                        emptyList()
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        packageNames = disService.discoverServices()
                    }) {
                        Text("Get all the package names")
                    }
                    if (packageNames.isEmpty()) {
                        Text("No Packages found")
                    } else {
                        packageNames.forEach {
                            println(it)
                            Text(it.packageName)
                            Text(it.serviceClassName)
                            HorizontalDivider(modifier = Modifier.padding(12.dp))
                        }

                    }
                }
            }
        }
    }
}

