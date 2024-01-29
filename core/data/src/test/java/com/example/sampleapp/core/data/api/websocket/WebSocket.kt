package com.example.sampleapp.core.data.api.websocket

import com.example.sampleapp.core.data.websocket.WebSocketManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import okio.ByteString
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class WebSocket {
    private val webSocketManger = WebSocketManger()

    @DisplayName("WebSocket_기본_동작_테스트")
    @Test
    fun webSocketTest() {
        runBlocking(Dispatchers.IO) {
            val listener = UpBitWebSocketListener(webSocketManger)
            webSocketManger.createConnectWebSocket(
                1234,
                "wss://api.upbit.com/websocket/v1",
                listener
            )
            webSocketManger.printConnectedWebSocketStatus("webSocketTest")
            delay(10000)
        }
    }

    @DisplayName("WebSocket_Clear_테스트")
    @Test
    fun webSocketClearTest() {
        runBlocking(Dispatchers.IO) {
            webSocketTest()

            webSocketManger.clearConnectWebSocket()
            delay(10000)
        }
    }

    @DisplayName("WebSocket_Reconnect_테스트")
    @Test
    fun webSocketReconnectTest() {
        runBlocking(Dispatchers.IO) {
            webSocketTest()

            webSocketManger.reconnectWebSocket(1234)
            delay(10000)
        }
    }

    class UpBitWebSocketListener(private val webSocketManger: WebSocketManger) :
        WebSocketManger.DefaultWebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            println("WebSocketListener onClosed code = $code, reason = $reason")
            webSocketManger.printConnectedWebSocketStatus("onClosed")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            println("WebSocketListener onClosing code = $code, reason = $reason")
            webSocketManger.printConnectedWebSocketStatus("onClosing")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            println("WebSocketListener onFailure response = $response, throwable = $t")
            webSocketManger.printConnectedWebSocketStatus("onFailure")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            println("WebSocketListener onMessage text = $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            println("WebSocketListener onMessage ByteString = ${bytes.string(StandardCharsets.UTF_8)}")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            println("WebSocketListener onOpen response = $response")
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
            webSocketManger.printConnectedWebSocketStatus("onOpen")
        }
    }
}
