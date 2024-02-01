package com.example.sampleapp.feature.main.services

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.sampleapp.core.data.event.EventObserver

class ClosingService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("SampleApplication", "ClosingService onCreate")
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        Log.d("SampleApplication", "ClosingService onTaskRemoved")

        EventObserver.eventPublisher.tryEmit(EventObserver.Event("SampleApplication", Bundle()))
        // Destroy the service
        stopSelf()
    }
}