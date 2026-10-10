package com.github.amanbutnot.bridge.discovery

import android.content.Intent
import android.content.pm.PackageManager

class BridgeServiceDiscovery(private val packageManager: PackageManager) {
    private val intent = Intent("com.github.amanbutnot.bridge_service")
    fun discoverServices(): List<DiscoveredBridgeService> {

        val services = packageManager.queryIntentServices(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val packageNames = services.map { service ->
            DiscoveredBridgeService(
                packageName = service.serviceInfo.packageName,
                serviceClassName = service.serviceInfo.name
            )
        }

        return packageNames
    }

}

data class DiscoveredBridgeService(
    val packageName: String,
    val serviceClassName: String
)