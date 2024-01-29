package com.example.sampleapp.core.data.websocket

import com.example.sampleapp.core.data.di.ApiModule
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.TimeUnit

class WebSocketMangerImp : WebSocketManager {
    private val connectedWebSocketHashMap: HashMap<Int, ConnectedWebSocketResult> = hashMapOf()

    @TestOnly
    fun printConnectedWebSocketStatus(tag: String) {
        if (connectedWebSocketHashMap.isEmpty()) {
            println("$tag printConnectedWebSocketStatus() connectedWebSocketHashMap is Empty")
        }

        connectedWebSocketHashMap.forEach {
            val key = it.key
            val value = it.value
            println("$tag printConnectedWebSocketStatus() connectedWebSocketHashMap key = $key, value = $value")
        }
    }

    @Throws(Exception::class)
    override fun createConnectWebSocket(id: Int, url: String, listener: WebSocketListener) {
        runCatching {
            Builder()
                .request(url)
                .listener(listener)
                .connect()
        }.onSuccess { webSocket ->
            connectedWebSocketHashMap[id] = webSocket
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(Exception::class)
    override fun createConnectWebSocket(id: Int, request: Request, listener: WebSocketListener) {
        runCatching {
            Builder()
                .request(request)
                .listener(listener)
                .connect()
        }.onSuccess { webSocket ->
            connectedWebSocketHashMap[id] = webSocket
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(Exception::class)
    override fun reconnectWebSocket(id: Int) {
        runCatching {
            val socket = findConnectedWebSocket(id)

            //재연결 작업 시작
            disconnectWebSocket(id)
            createConnectWebSocket(id, socket.request, socket.listener)
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(Exception::class)
    override fun disconnectWebSocket(id: Int) {
        runCatching {
            val socket = findConnectedWebSocket(id)
            val isClose = socket.webSocket.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)

            if(isClose)
                return@runCatching socket
            else
                throw Throwable("disconnectWebSocket() onSuccess isClose is failure")
        }.onSuccess { webSocket ->
            connectedWebSocketHashMap.remove(id)
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(ArrayIndexOutOfBoundsException::class, NullPointerException::class)
    private fun findConnectedWebSocket(id: Int) =
        connectedWebSocketHashMap[id] ?: throw NullPointerException("$TAG findConnectedWebSocket() is null")

    @Throws(Exception::class)
    override fun clearConnectWebSocket() {
        connectedWebSocketHashMap.forEach {
            val key = it.key
            val value = it.value

            runCatching {
                val isClose = value.webSocket.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)

                if(isClose)
                    return@runCatching value
                else
                    throw Throwable("clearConnectWebSocket() onSuccess isClose is failure")
            }.onSuccess { webSocket ->
                connectedWebSocketHashMap.remove(key)
            }.onFailure { error ->
                error.printStackTrace()
                throw error
            }
        }
    }

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

        /**
         * 소켓 통신 연결 데이터
         *  - request(필수)
         *  - listener(필수)
         *
         * @throws Exception
         * @throws NullPointerException
         * @return ConnectedWebSocketResult
         */
        @Throws(Exception::class, NullPointerException::class)
        fun connect(): ConnectedWebSocketResult {
            if (request == null)
                throw NullPointerException("$TAG connect() request is null. Please not null")

            if (webSocketListener == null)
                throw NullPointerException("$TAG connect() webSocketListener is null. Please not null")

            //소켓 생성
            val task = runCatching {
                client.newWebSocket(request!!, webSocketListener!!)
            }.onFailure { error ->
                error.printStackTrace()
                throw error
            }

            //소켓 생성 검증
            val result = task.getOrNull()
            return if (result == null) {
                throw NullPointerException("$TAG connect() result is null. Please not null")
            } else {
                ConnectedWebSocketResult(result, request!!, webSocketListener!!)
            }
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

    /**
     * 생성된 소켓 관련 결과 데이터
     *
     * @property webSocket 연결된 소켓 Instance
     * @property request   연결된 소켓 Request Instance
     * @property listener  연결된 소켓 Listener Instance
     */
    data class ConnectedWebSocketResult(
        val webSocket: WebSocket,
        val request: Request,
        val listener: WebSocketListener
    )

    companion object {
        const val TAG = "WebSocketManger"

        /**
         * https://datatracker.ietf.org/doc/html/rfc6455#section-7.4
         * 기본 Close Frame Code
         */
        const val WEB_SOCKET_FINISH_CODE = 1000
        const val WEB_SOCKET_FINISH_MESSAGE = "CLIENT REQUEST FINISH"
    }

}