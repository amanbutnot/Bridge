package com.github.amanbutnot.bridge.connectivity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.github.amanbutnot.bridge.discovery.DiscoveredBridgeService

class BridgeServiceConnector(discovery: DiscoveredBridgeService, private val context: Context) {
   private val component = ComponentName(discovery.packageName, discovery.serviceClassName)
    private val intent = Intent().setComponent(component)
    private val serviceConnector = object : ServiceConnection {
        override fun onServiceConnected(
            p0: ComponentName?,
            p1: IBinder?
        ) {
            println("Service connected successfully and binder is: $p1")
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            println("Service disconnected successfully")
        }
    }

    fun connect(): Boolean {
        return context.bindService(intent, serviceConnector, Context.BIND_AUTO_CREATE)
    }
}
