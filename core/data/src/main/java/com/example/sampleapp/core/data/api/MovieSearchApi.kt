package com.example.sampleapp.core.data.api

import com.example.sampleapp.core.data.api.model.ContributorResponse
import com.example.sampleapp.core.data.api.model.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieSearchApi {
    /**
     * 영화 장르 코드
     * - 1: 드라마
     * - 2: 판타지
     * - 3: 서부
     * - 4: 공포
     * - 5: 로맨스
     * - 6: 모험
     * - 7: 스릴러
     * - 8: 느와르
     * - 9: 컬트
     * - 10: 다큐멘터리
     * - 11: 코미디
     * - 12: 가족
     * - 13: 미스터리
     * - 14: 전쟁
     * - 15: 애니메이션
     * - 16: 범죄
     * - 17: 뮤지컬
     * - 18: SF
     * - 19: 액션
     * - 20: 무협
     * - 21: 에로
     * - 22: 서스펜스
     * - 23: 서사
     * - 24: 블랙코미디
     * - 25: 실험
     * - 26: 영화카툰
     * - 27: 영화음악
     * - 28: 영화패러디포스터
     *
     * 국가 코드
     * - FR: 프랑스
     * - GB: 영국
     * - HK: 홍콩
     * - JP: 일본
     * - KR: 한국
     * - US: 미국
     * - ETC: 기타
     */
    @GET("repos/{owner}/{name}/contributors")
    suspend fun getMovies(
        @Query("query") query: String, //필수여부 : Y, 검색어. UTF-8로 인코딩되어야 합니다.
        @Query("genre") genre: String?,  //필수여부 : N, 영화 장르 코드
        @Query("country") country: String?,  //필수여부 : N, 국가 코드. 영문 대문자만 사용할 수 있습니다.
        @Query("yearfrom") yearFrom: Int?,   //필수여부 : N, 제작 연도 검색 시작 연도(yyyy 형식). 특정 기간에 제작한 영화를 검색합니다. yearto 파라미터와 함께 사용해야 합니다.
        @Query("yearto") yearTo: Int?,   //필수여부 : N, 검색어. 제작 연도 검색 종료 연도(yyyy 형식). 특정 기간에 제작한 영화를 검색합니다. yearfrom 파라미터와 함께 사용해야 합니다.
        @Query("start") start: Int = 1, //필수여부 : N, 검색 시작 위치(기본값: 1, 최댓값: 1000)
        @Query("display") display: Int = NAVER_DISPLAY_DEFAULT_COUNT,   //필수여부 : N, 한 번에 표시할 검색 결과 개수(기본값: 10, 최댓값: 100)
    ): MovieSearchResponse


    companion object{
        const val NAVER_CLIENT_ID = "77VcsUrDmA2d3B4LkguK"
        const val NAVER_CLIENT_SECRET = "MHy3E25Hxy"
        const val NAVER_DISPLAY_DEFAULT_COUNT = 20
    }
}
