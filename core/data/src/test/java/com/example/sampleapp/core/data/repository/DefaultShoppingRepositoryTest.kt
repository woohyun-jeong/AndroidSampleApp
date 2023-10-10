package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.api.ShoppingSearchApi
import com.example.sampleapp.core.data.di.ApiModule
import com.example.sampleapp.core.model.Shopping
import io.kotest.common.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue


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

    @DisplayName("getSearchShopping_기본_동작_테스트")
    @Test
    fun getSearchShopping() {
        runBlocking {
            val flow1 = defaultMovieRepository?.getSearchShopping(
                query = "가",
                sort = ShoppingSearchApi.ShoppingSort.dsc,
                start = 1
            ) ?: return@runBlocking

            val flow2 = defaultMovieRepository?.getSearchShopping(
                query = "가",
                sort = ShoppingSearchApi.ShoppingSort.asc,
                start = 1
            ) ?: return@runBlocking

            flow1.combine(flow2) { t1, t2 ->
                Pair(t1, t2)
            }.collect{
                val result1 = it.first.first()
                val result2 = it.second.first()
                println("getSearchShopping_기본_동작_테스트 result1 first lPrice = ${result1.lPrice}")
                println("getSearchShopping_기본_동작_테스트 result2 first lPrice = ${result2.lPrice}")
                assertTrue(result1.lPrice > result2.lPrice)
            }
        }
    }

}