package com.example.sampleapp.core.data.di

import com.example.sampleapp.core.data.api.GithubApi
import com.example.sampleapp.core.data.api.GithubRawApi
import com.example.sampleapp.core.data.api.MovieSearchApi
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
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {

    @Provides
    @Singleton
    fun provideOkhttpClient(
        interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideNaverInterceptor(
        json: Json,
    ): NaverAuthInterceptor {
        return NaverAuthInterceptor()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(
        json: Json,
    ): Converter.Factory {
        return json.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideMovieSearchApi(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): MovieSearchApi = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/v1/search/movie.json")
        .addConverterFactory(converterFactory)
        .client(okHttpClient).build()
        .create(MovieSearchApi::class.java)

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}
