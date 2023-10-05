package com.example.sampleapp.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO : SerialName 영어로 변경
 */
@Serializable
data class MovieSearchResponse (
    @SerialName("title") val title: String,
    @SerialName("link") val link: String,
    @SerialName("description") val description: String,
    @SerialName("lastBuildDate") val lastBuildDate: String, //검색 결과를 생성한 시간
    @SerialName("total") val total: Int,    //총 검색 결과 개수
    @SerialName("start") val start: Int,    //검색 시작 위치
    @SerialName("display") val display: Int,    //한 번에 표시할 검색 결과 개수
    @SerialName("items") val items: List<MovieItemResponse>,
)
