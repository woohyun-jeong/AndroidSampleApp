package com.example.sampleapp.feature.sample.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.sampleapp.core.data.event.NetworkChangeEventObserver

class ConnectivityWatcher(
    private val context: Context
) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            networkCallback = createNetworkCallback()
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            val intentFilter = IntentFilter(CONNECTIVITY_ACTION)
            broadcastReceiver = createBroadcastReceiver()
            context.registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    override fun onInactive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        @RequiresApi(Build.VERSION_CODES.M)
//        override fun onCapabilitiesChanged(
//            network: Network,
//            networkCapabilities: NetworkCapabilities
//        ) {
//            val isInternet = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET)
//            val isValidated = networkCapabilities.hasCapability(NET_CAPABILITY_VALIDATED)
//            postValue(isInternet && isValidated)
//            NetworkChangeEventObserver.eventPublisher.tryEmit(NetworkChangeEventObserver.Event(isInternet && isValidated))
//        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            NetworkChangeEventObserver.eventPublisher.tryEmit(NetworkChangeEventObserver.Event(true))

        }

        override fun onLost(network: Network) {
            postValue(false)
            NetworkChangeEventObserver.eventPublisher.tryEmit(NetworkChangeEventObserver.Event(false))

        }
    }

    fun createBroadcastReceiver() = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isNoConnectivity = intent?.extras?.getBoolean(EXTRA_NO_CONNECTIVITY) ?: true
            postValue(!isNoConnectivity)
            NetworkChangeEventObserver.eventPublisher.tryEmit(NetworkChangeEventObserver.Event(!isNoConnectivity))
        }
    }

    //https://yoon-dailylife.tistory.com/123
}