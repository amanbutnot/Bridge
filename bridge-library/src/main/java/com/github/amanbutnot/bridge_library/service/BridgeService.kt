package com.github.amanbutnot.bridge_library.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


class BridgeService : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}