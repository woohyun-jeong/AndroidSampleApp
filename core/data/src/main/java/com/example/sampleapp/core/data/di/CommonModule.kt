package com.example.sampleapp.core.data.di

import com.example.sampleapp.core.data.websocket.WebSocketMangerImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CommonModule {
    @Provides
    @Singleton
    fun provideWebSocketMangerImp(): WebSocketMangerImp = WebSocketMangerImp()
}