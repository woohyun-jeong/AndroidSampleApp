package com.example.sampleapp.core.data.websocket

import okhttp3.Request
import okhttp3.WebSocketListener

interface WebSocketManager {

    /**
     * 지정한 ID WebSocket 생성
     *
     * @throws Exception
     * @param id
     * @param url
     * @param listener
     */
    fun createConnectWebSocket(id: Int, url: String, listener: WebSocketListener)

    /**
     * 지정한 ID WebSocket 생성
     *
     * @throws Exception
     * @param id
     * @param request
     * @param listener
     */
    fun createConnectWebSocket(id: Int, request: Request, listener: WebSocketListener)

    /**
     * 지정한 ID WebSocket 재연결 실행
     *
     * @param id WebSocket 생성할 때 지정한 id
     */
    fun reconnectWebSocket(id: Int)

    /**
     * 지정한 ID WebSocket 연결 닫기 실행
     *
     * 주의사항)
     * - 정상적으로 소켓이 닫혔을때는 불가능(이미 hashMap에서 삭제되었기 때문에)
     * - 소켓 연결 시, onFailure을 탔을때 기존 소켓 등록 정보로 다시 연결하기 위해 만들어짐
     *
     * @param id WebSocket 생성할 때 지정한 id
     */
    fun disconnectWebSocket(id: Int)

    /**
     * 연결된 모든 WebSocket 연결 닫기 실행
     *
     * @throws Exception
     */
    fun clearConnectWebSocket()

}