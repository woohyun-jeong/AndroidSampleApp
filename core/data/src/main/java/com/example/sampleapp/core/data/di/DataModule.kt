package com.example.sampleapp.core.data.di

import com.example.sampleapp.core.data.repository.DefaultShoppingRepository
import com.example.sampleapp.core.data.repository.ShoppingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsShoppingRepository(
        repository: DefaultShoppingRepository,
    ): ShoppingRepository

}
