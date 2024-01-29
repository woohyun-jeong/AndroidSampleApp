package com.example.sampleapp.core.data.websocket

import android.util.Log
import com.example.sampleapp.core.data.di.ApiModule
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.TimeUnit

class WebSocketManger {
    private val connectedWebSocketHashMap: HashMap<Int, ConnectedWebSocketResult> = hashMapOf()

    @TestOnly
    fun printConnectedWebSocketStatus(tag: String) {
        if (connectedWebSocketHashMap.isEmpty()) {
            println("$tag printConnectedWebSocketStatus() is Empty")
            return
        }

        connectedWebSocketHashMap.forEach {
            val key = it.key
            val value = it.value

            val isClose = value.listener.isClose
            val isFailure = value.listener.isFailure
            println("$tag printConnectedWebSocketStatus() key = $key, value = $value, isClose = $isClose, isFailure = $isFailure")
        }
    }

    /**
     * TODO
     *
     * @throws Exception
     * @param id
     * @param url
     * @param listener
     */
    @Throws(Exception::class)
    fun createConnectWebSocket(id: Int, url: String, listener: DefaultWebSocketListener) {
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

    /**
     * TODO
     *
     * @throws Exception
     * @param id
     * @param request
     * @param listener
     */
    @Throws(Exception::class)
    fun createConnectWebSocket(id: Int, request: Request, listener: DefaultWebSocketListener) {
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
    fun reconnectWebSocket(id: Int) {
         runCatching {
            val socket = findWebSocket(id)

             //재연결 작업 시작
             disconnectWebSocket(id)
             createConnectWebSocket(id, socket.request, socket.listener)
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    /**
     * 지정한 ID WebSocket 연결 닫기 실행
     *
     * @param id WebSocket 생성할 때 지정한 id
     */
    @Throws(Exception::class)
    fun disconnectWebSocket(id: Int) {
        runCatching {
            val socket = findWebSocket(id)

            if(socket.listener.isClose) {
                Log.d(TAG, "disconnectWebSocket() isClose is true, not Close Task")
                return@runCatching
            }

            socket.webSocket.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)
        }.onSuccess {
            connectedWebSocketHashMap.remove(id)
        }.onFailure { error ->
            error.printStackTrace()
            throw error
        }
    }

    @Throws(ArrayIndexOutOfBoundsException::class, NullPointerException::class)
    private fun findWebSocket(id: Int) = connectedWebSocketHashMap[id] ?: throw NullPointerException("$TAG findWebSocket() is null")

    /**
     * 연결된 모든 WebSocket 연결 닫기 실행
     *
     * @throws Exception
     */
    @Throws(Exception::class)
    fun clearConnectWebSocket() {
        connectedWebSocketHashMap.forEach {
            val key = it.key
            val value = it.value

            runCatching {
                if(value.listener.isClose) {
                    Log.d(TAG, "clearConnectWebSocket() isClose is true, not Close Task")
                    return@runCatching
                }

                value.webSocket.close(WEB_SOCKET_FINISH_CODE, WEB_SOCKET_FINISH_MESSAGE)
            }.onSuccess {
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
        private var webSocketListener: DefaultWebSocketListener? = null

        fun request(request: Request): Builder {
            this@Builder.request = request
            return this@Builder
        }

        fun request(url: String): Builder {
            val request = createRequest(url)
            this@Builder.request = request
            return this@Builder
        }

        fun listener(webSocketListener: DefaultWebSocketListener): Builder {
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
        val listener: DefaultWebSocketListener
    )

    open class DefaultWebSocketListener : WebSocketListener() {
        var isClose: Boolean = true
        var isFailure: Boolean = false

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            isClose = true
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            isClose = true
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            isClose = true
            isFailure = true
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            isClose = false
            isFailure = false
        }

    }

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