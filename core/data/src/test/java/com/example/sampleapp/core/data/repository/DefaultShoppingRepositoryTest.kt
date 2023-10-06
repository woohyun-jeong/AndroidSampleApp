package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.di.ApiModule
import io.kotest.common.runBlocking
import kotlinx.coroutines.delay
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


class DefaultShoppingRepositoryTest {
    private var defaultMovieRepository: DefaultShoppingRepository? = null

    @BeforeEach
    fun setUp() {
        defaultMovieRepository = DefaultShoppingRepository(
            ApiModule.provideShoppingSearchApi(
                ApiModule.provideOkhttpClient(
                    ApiModule.providesLoggingInterceptor(),
                    ApiModule.provideNaverInterceptor()
                ),
                ApiModule.provideConverterFactory(ApiModule.provideJson())
            )
        )
    }

    @DisplayName("getAllShopping_기본_동작_테스트")
    @Test
    fun getAllShopping() {
        runBlocking {
            defaultMovieRepository
                ?.getAllShopping()
                ?.collect {
                    Assert.assertNotNull(it)
                    Assert.assertTrue(it.isNotEmpty())
                    println("getAllShopping_기본_동작_테스트 result = $it")
                }
        }
    }

}