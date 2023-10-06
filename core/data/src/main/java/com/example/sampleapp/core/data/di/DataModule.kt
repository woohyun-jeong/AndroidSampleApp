package com.example.sampleapp.core.data.di

import android.content.Context
import com.example.sampleapp.core.data.api.GithubRawApi
import com.example.sampleapp.core.data.api.MovieSearchApi
import com.example.sampleapp.core.data.api.fake.AssetsGithubRawApi
import com.example.sampleapp.core.data.repository.ContributorRepository
import com.example.sampleapp.core.data.repository.DefaultContributorRepository
import com.example.sampleapp.core.data.repository.DefaultMovieRepository
import com.example.sampleapp.core.data.repository.DefaultSessionRepository
import com.example.sampleapp.core.data.repository.DefaultSettingsRepository
import com.example.sampleapp.core.data.repository.DefaultSponsorRepository
import com.example.sampleapp.core.data.repository.MovieRepository
import com.example.sampleapp.core.data.repository.SessionRepository
import com.example.sampleapp.core.data.repository.SettingsRepository
import com.example.sampleapp.core.data.repository.SponsorRepository
import com.example.sampleapp.core.datastore.datasource.DefaultSessionPreferencesDataSource
import com.example.sampleapp.core.datastore.datasource.SessionPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

//    @Binds
//    abstract fun bindsMovieRepository(
//        repository: DefaultMovieRepository,
//    ): MovieRepository

}
