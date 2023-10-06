package com.example.sampleapp.core.data.api

import com.example.sampleapp.core.data.api.model.ShoppingSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ShoppingSearchApi {
    /**
     * 	검색 결과 정렬 방법
     * - sim: 정확도순으로 내림차순 정렬(기본값)
     * - date: 날짜순으로 내림차순 정렬
     * - asc: 가격순으로 오름차순 정렬
     * - dsc: 가격순으로 내림차순 정렬
     */
    @GET("shop.json")
    suspend fun getShoppingItemsSort(
        @Query("query", encoded = true) query: String, //필수여부 : Y, 검색어. UTF-8로 인코딩되어야 합니다.
        @Query("start") start: Int = 1, //필수여부 : N, 검색 시작 위치(기본값: 1, 최댓값: 1000)
        @Query("display") display: Int = NAVER_DISPLAY_DEFAULT_COUNT,   //필수여부 : N, 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
        @Query("sort") sort: String?,   //필수여부 : N, 검색 결과 정렬 방법
    ): ShoppingSearchResponse

    @GET("shop.json")
    suspend fun getShoppingItems(
        @Query("query", encoded = true) query: String, //필수여부 : Y, 검색어. UTF-8로 인코딩되어야 합니다.
        @Query("start") start: Int = 1, //필수여부 : N, 검색 시작 위치(기본값: 1, 최댓값: 1000)
        @Query("display") display: Int = NAVER_DISPLAY_DEFAULT_COUNT,   //필수여부 : N, 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
    ): ShoppingSearchResponse

    companion object{
        const val NAVER_CLIENT_ID = "77VcsUrDmA2d3B4LkguK"
        const val NAVER_CLIENT_SECRET = "MHy3E25Hxy"
        const val NAVER_DISPLAY_DEFAULT_COUNT = 20
        const val NAVER_QUERY_DEFAULT = "가"
    }
}
