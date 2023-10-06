package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.di.ApiModule
import io.kotest.common.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class DefaultShoppingRepositoryTest {
    private var defaultMovieRepository: DefaultShoppingRepository? = null

    @BeforeEach
    fun setUp() {
        defaultMovieRepository = DefaultShoppingRepository(ApiModule.provideShoppingSearchApi(
            ApiModule.provideOkhttpClient(ApiModule.providesLoggingInterceptor(), ApiModule.provideNaverInterceptor()),
            ApiModule.provideConverterFactory(ApiModule.provideJson())
        ))
    }

    @Test
    fun `getAllShopping_기본_동작_테스트`() {
        runBlocking {
            defaultMovieRepository
                ?.getAllShopping()
                ?.collect{
                    println("getAllMovies result = $it")
                }
        }
    }

}