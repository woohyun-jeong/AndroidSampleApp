package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.datastore.datasource.SettingsPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DefaultSettingsRepository @Inject constructor(
    private val preferencesDataSource: SettingsPreferencesDataSource
) : SettingsRepository {

    override fun getIsDarkTheme(): Flow<Boolean> =
        preferencesDataSource.settingsData.map { settingsData -> settingsData.isDarkTheme }

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        preferencesDataSource.updateIsDarkTheme(isDarkTheme)
    }
}
