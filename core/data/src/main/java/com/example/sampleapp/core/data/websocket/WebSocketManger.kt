package com.example.sampleapp.core.data.websocket

import com.example.sampleapp.core.data.di.ApiModule
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

class WebSocketManger {
    private val connectedWebSocketHashMap: HashMap<Int, WebSocket> = hashMapOf()

    fun createConnectWebSocket(id: Int, url: String, listener: WebSocketListener) {
        runCatching {
            Builder()
                .request(url)
                .listener(listener)
                .connect()
        }.onSuccess { webSocket ->
            connectedWebSocketHashMap[id] = webSocket
        }.onFailure {
            it.printStackTrace()
        }
    }

    @Throws(Exception::class)
    fun disconnectWebSocket(id: Int) {
        runCatching {
            connectedWebSocketHashMap[id]
        }.onSuccess { webSocket ->
            webSocket?.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(Exception::class)
    fun clearConnectWebSocket() {
        connectedWebSocketHashMap.forEach {
            val websocket = it.value

            runCatching {
                websocket.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)
            }.onFailure { error ->
                error.printStackTrace()
                throw error
            }
        }
    }

    /**
     * TODO
     *
     */
    class Builder {
        private val client: OkHttpClient = provideWebSocketClient()
        private var request: Request? = null
        private var webSocketListener: WebSocketListener? = null

        fun request(request: Request): Builder {
            this@Builder.request = request
            return this@Builder
        }

        fun request(url: String): Builder {
            val request = createRequest(url)
            this@Builder.request = request
            return this@Builder
        }

        fun listener(webSocketListener: WebSocketListener): Builder {
            this@Builder.webSocketListener = webSocketListener
            return this@Builder
        }

        @Throws(NullPointerException::class)
        fun connect(): WebSocket {
            if (request == null)
                throw NullPointerException("$TAG connect() request is null. Please not null")

            if (webSocketListener == null)
                throw NullPointerException("$TAG connect() webSocketListener is null. Please not null")

            return client.newWebSocket(request!!, webSocketListener!!)
        }

        private fun createRequest(url: String) = Request.Builder()
            .url(url)
            .build()

        private fun provideWebSocketClient(): OkHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(ApiModule.providesLoggingInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .build()
    }

    companion object {
        const val TAG = "WebSocketManger"
        const val WEB_SOCKET_FINISH_CODE = 999
        const val WEB_SOCKET_FINISH_MESSAGE = "CLIENT REQUEST FINISH"
    }

}