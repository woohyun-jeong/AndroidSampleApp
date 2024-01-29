package com.example.sampleapp.core.data.api.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object WebSocketTestClient {
    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .pingInterval(30, TimeUnit.SECONDS)
        .build()
    val request = Request.Builder()
        .url("wss://api.upbit.com/websocket/v1")
        .build()

    val listener: WebSocketListener = WebSocketListener()
    var webSocket: WebSocket? = null
}