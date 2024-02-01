package com.example.sampleapp.feature.sample.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sampleapp.core.data.event.EventObserver
import com.example.sampleapp.core.data.websocket.WebSocketMangerImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.StandardCharsets


class SampleWebsocketService : Service() {
    private val mBinder: IBinder = WebSocketsBinder()
    private var webSocketManagerImp: WebSocketMangerImp = WebSocketMangerImp()
    //Define a LiveData to observe in activity
    val textLiveData = MutableLiveData<String>()

    /**
     * @brief Network change
     */
//    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val networkIsOn = intent.getBooleanExtra(ACTION_NETWORK_STATE_CHANGED, false)
//            if (networkIsOn) {
//                startSocket()
//            } else {
//                stopSocket()
//            }
//        }
//    }

    override fun onBind(intent: Intent?): IBinder {
        Log.i(tag, "onBind")
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//            mMessageReceiver, IntentFilter(
//                ACTION_NETWORK_STATE_CHANGED
//            )
//        )
        startSocket()
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(tag, "onUnbind")
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        stopSocket()
        return false
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

    private fun sendMessageReceivedEvent(text: String) {
        CoroutineScope(Dispatchers.Main).launch {
            textLiveData.value = text
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
            sendMessageReceivedEvent(text)

        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)

            EventObserver.eventPublisher.tryEmit(EventObserver.Event(id.toString(), Bundle().apply {
                putString("message", bytes.string(StandardCharsets.UTF_8))

            }))

            sendMessageReceivedEvent(bytes.string(StandardCharsets.UTF_8))

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
        const val ACTION_NETWORK_STATE_CHANGED = "networkStateChanged"
        private val tag = "SampleWebsocketService"
        private val id = 1234
        private const val WS_URL = "wss://api.upbit.com/websocket/v1"
    }

}