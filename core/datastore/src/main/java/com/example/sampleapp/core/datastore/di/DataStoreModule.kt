package com.example.sampleapp.core.datastore.di

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sampleapp.core.datastore.TestData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val SETTING_DATASTORE_NAME = "SETTINGS_PREFERENCES"
    private const val SESSION_DATASTORE_NAME = "SESSION_PREFERENCES"
    private const val TEST_DATASTORE_FILE_NAME = "TEST_DATA.pb"
    private val Context.settingDataStore by preferencesDataStore(SETTING_DATASTORE_NAME)
    private val Context.sessionDataStore by preferencesDataStore(SESSION_DATASTORE_NAME)
    private val Context.testDataStore by dataStore(
        fileName = TEST_DATASTORE_FILE_NAME,
        serializer = TestSerializer
    )

    object TestSerializer : Serializer<TestData> {
        override val defaultValue: TestData
            get() = TestData.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): TestData {
            try {
                return TestData.parseFrom(input)
            } catch (exception: Exception) {
                throw CorruptionException("Cannot read proto.", exception)
            }
        }

        override suspend fun writeTo(t: TestData, output: OutputStream) {
            t.writeTo(output)
        }

    }
    @Provides
    @Singleton
    @Named("setting")
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.settingDataStore

    @Provides
    @Singleton
    @Named("session")
    fun provideSessionDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.sessionDataStore

    @Provides
    @Singleton
    @Named("test")
    fun provideTestDataStore(
        @ApplicationContext context: Context
    ): DataStore<TestData> = context.testDataStore
}
