package com.example.sampleapp.core.data.api.interceptor

import com.example.sampleapp.core.data.BuildConfig
import com.example.sampleapp.core.data.api.ShoppingSearchApi
import okhttp3.Interceptor
import okhttp3.Response

class NaverAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val builder = req.newBuilder().apply {
            addHeader("X-Naver-Client-Id", BuildConfig.NAVER_CLIENT_ID)
            addHeader("X-Naver-Client-Secret", BuildConfig.NAVER_CLIENT_SECRET)
        }
        return chain.proceed(builder.build())
    }
}
