package com.example.sampleapp.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * TODO : SerialName 영어로 변경
 */
@Serializable
data class ShoppingItemResponse (
    @SerialName("title") var title: String, //영화 제목. 제목에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    @SerialName("link") var link: String,   //네이버 영화 정보 URL
    @SerialName("image") var image: String,   //섬네일 이미지의 URL
    @SerialName("lprice") var lPrice: String,   //최저가. 최저가 정보가 없으면 0을 반환합니다. 가격 비교 데이터가 없으면 상품 가격을 의미합니다.
    @SerialName("hprice") var hPrice: String,   //최고가. 최고가 정보가 없거나 가격 비교 데이터가 없으면 0을 반환합니다.
    @SerialName("mallName") var mallName: String,   //상품을 판매하는 쇼핑몰. 쇼핑몰 정보가 없으면 네이버를 반환합니다.
    @SerialName("productId") var productId: String,   //네이버 쇼핑의 상품 ID
    @SerialName("productType") var productType: String,   //	상품군과 상품 종류에 따른 상품 타입.
    @SerialName("maker") var maker: String,   //제조사
    @SerialName("brand") var brand: String,   //브랜드
    @SerialName("category1") var category1: String,   //상품의 카테고리(대분류)
    @SerialName("category2") var category2: String,   //상품의 카테고리(중분류)
    @SerialName("category3") var category3: String,   //상품의 카테고리(소분류)
    @SerialName("category4") var category4: String,   //상품의 카테고리(세분류)
)
