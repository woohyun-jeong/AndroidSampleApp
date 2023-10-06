package com.example.sampleapp.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO : SerialName 영어로 변경
 */
@Serializable
data class ShoppingSearchResponse (
    @SerialName("lastBuildDate") var lastBuildDate: String, //검색 결과를 생성한 시간
    @SerialName("total") var total: Int,    //총 검색 결과 개수
    @SerialName("start") var start: Int,    //검색 시작 위치
    @SerialName("display") var display: Int,    //한 번에 표시할 검색 결과 개수
    @SerialName("items") var items: List<ShoppingItemResponse>,
)
