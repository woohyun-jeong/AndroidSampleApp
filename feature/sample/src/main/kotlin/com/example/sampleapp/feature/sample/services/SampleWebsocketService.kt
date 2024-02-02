package com.example.sampleapp.feature.sample.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.sampleapp.core.data.event.EventObserver
import com.example.sampleapp.core.data.event.NetworkChangeEventObserver
import com.example.sampleapp.core.data.websocket.WebSocketMangerImp
import com.example.sampleapp.feature.sample.broadcast.ConnectivityWatcher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class SampleWebsocketService : Service() {
    private val mBinder: IBinder = WebSocketsBinder()
    @Inject
    lateinit var webSocketManagerImp: WebSocketMangerImp
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var broadcastReceiver: BroadcastReceiver
    private var isNetworkConnected = true

    override fun onBind(intent: Intent?): IBinder {
        Log.i(tag, "onBind")
        startSocket()
        onActive(this)
        CoroutineScope(Dispatchers.IO).launch {
            NetworkChangeEventObserver.eventSubscriber.collect {
                if (it.isNetworkChange) {
                    if (!isNetworkConnected) {
                        startSocket()
                        isNetworkConnected = true
                        Log.d("AAAA", "network connected")
                    }
                } else {
                    if (isNetworkConnected) {
                        stopSocket()
                        isNetworkConnected = false
                        Log.d("AAAA", "network disconnected")
                    }

                }
            }
        }
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(tag, "onUnbind")
        stopSocket()
        onInactive(this)
        return false
    }

    fun onActive(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            networkCallback = ConnectivityWatcher(context).createNetworkCallback()
            cm.registerNetworkCallback(NetworkRequest.Builder().apply {
                addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            }.build(), networkCallback)
        } else {
            val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            broadcastReceiver = ConnectivityWatcher(context).createBroadcastReceiver()
            context.registerReceiver(broadcastReceiver, intentFilter)
        }
    }

    fun onInactive(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            cm.unregisterNetworkCallback(networkCallback)
        } else {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    private fun startSocket() {
        Log.i(tag, "startSocket")

        runCatching {
            webSocketManagerImp.createConnectWebSocket(
                id = id,
                url = WS_URL,
                listener = listener
            )
        }.onFailure { error ->
            Log.i(tag, "error")

            error.printStackTrace()
        }

    }

    private fun stopSocket() {
        runCatching {
            webSocketManagerImp.clearConnectWebSocket()
        }.onFailure { error ->
            error.printStackTrace()
        }
    }

    inner class WebSocketsBinder : Binder() {
        val service: SampleWebsocketService
            get() = this@SampleWebsocketService
    }

    val listener = object : WebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d(tag, "onClosed code = $code, reason = $reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Log.d(tag, "onClosing code = $code, reason = $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.d(
                tag,
                "onFailure webSocket = $webSocket, response = ${response?.body?.string()}"
            )
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            EventObserver.eventPublisher.tryEmit(EventObserver.Event(id.toString(), Bundle().apply {
                putString("message", bytes.string(StandardCharsets.UTF_8))
            }))

        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Log.d(
                tag,
                "onOpen webSocket = $webSocket, response = ${response.body?.string()}"
            )
            val testRequest = "[\n" +
                    "  {\n" +
                    "    \"ticket\": \"test example\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"type\": \"ticker\",\n" +
                    "    \"codes\": [\n" +
                    "      \"KRW-BTC\",\n" +
                    "      \"KRW-ETH\"\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"format\": \"DEFAULT\"\n" +
                    "  }\n" +
                    "]"
            webSocket.send(testRequest)
        }
    }


    companion object {
        private val tag = "SampleWebsocketService"
        private val id = 1234
        private const val WS_URL = "wss://api.upbit.com/websocket/v1"
    }

}