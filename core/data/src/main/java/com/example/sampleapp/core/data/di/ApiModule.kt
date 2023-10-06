package com.example.sampleapp.core.data.di

import com.example.sampleapp.core.data.api.ShoppingSearchApi
import com.example.sampleapp.core.data.api.interceptor.NaverAuthInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkhttpClient(
        logging: HttpLoggingInterceptor,
        auth: NaverAuthInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(auth)
        .addNetworkInterceptor(logging)
        .build()

    @Provides
    @Singleton
    fun provideNaverInterceptor(): NaverAuthInterceptor = NaverAuthInterceptor()

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideShoppingSearchApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): ShoppingSearchApi = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/v1/search/")
        .addConverterFactory(converterFactory)
        .client(okHttpClient).build()
        .create(ShoppingSearchApi::class.java)

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

}
