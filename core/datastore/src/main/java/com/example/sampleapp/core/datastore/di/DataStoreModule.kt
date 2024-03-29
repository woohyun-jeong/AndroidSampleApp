package com.example.sampleapp.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sampleapp.core.datastore.TestData
import com.example.sampleapp.core.datastore.util.CryptoImpl
import com.example.sampleapp.core.datastore.util.TestSerializer
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.integration.android.AndroidKeystoreKmsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val SETTING_DATASTORE_NAME = "SETTINGS_PREFERENCES"
    private const val SESSION_DATASTORE_NAME = "SESSION_PREFERENCES"
    private const val TEST_DATASTORE_FILE_NAME = "TEST_DATA.pb"
    private const val KEYSET_NAME = "__androidx_encrypted_prefs_keyset__"
    private const val PREF_FILE_NAME = "androidx_secret_prefs"
    private const val MASTER_KEY_URI =
        "${AndroidKeystoreKmsClient.PREFIX}refresh_token_sample_master_key"

    private val Context.settingDataStore by preferencesDataStore(SETTING_DATASTORE_NAME)
    private val Context.sessionDataStore by preferencesDataStore(SESSION_DATASTORE_NAME)

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
        @ApplicationContext context: Context,
        aead: Aead
    ): DataStore<TestData> = DataStoreFactory.create(
        produceFile = { File(context.filesDir, "datastore/$TEST_DATASTORE_FILE_NAME") },
        serializer = TestSerializer(CryptoImpl(aead))
    )

    @Provides
    @Singleton
    fun aead(@ApplicationContext context: Context): Aead {
        AeadConfig.register()

        return AndroidKeysetManager
            .Builder()
            .withSharedPref(context, KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(Aead::class.java)
    }
}
