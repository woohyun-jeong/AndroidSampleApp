package com.example.sampleapp.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO : SerialName 영어로 변경
 */
@Serializable
data class MovieItemResponse (
    @SerialName("title") val title: String, //영화 제목. 제목에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    @SerialName("link") val link: String,   //네이버 영화 정보 URL
    @SerialName("image") val image: String,   //섬네일 이미지의 URL
    @SerialName("subtitle") val subtitle: String,   //영어 제목 또는 원제
    @SerialName("pubDate") val pubDate: String, //제작 연도(yyyy 형식)
    @SerialName("director") val director: String,   //감독
    @SerialName("actor") val actor: String, //출연 배우
    @SerialName("userRating") val userRating: String    //평점
)
