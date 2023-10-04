package com.example.sampleapp.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface SessionPreferencesDataSource {
    val bookmarkedSession: Flow<Set<String>>
    suspend fun updateBookmarkedSession(bookmarkedSession: Set<String>)
}
