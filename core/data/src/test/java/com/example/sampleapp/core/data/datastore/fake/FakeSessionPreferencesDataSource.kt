package com.example.sampleapp.core.data.datastore.fake

import com.example.sampleapp.core.datastore.datasource.SessionPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class FakeSessionPreferencesDataSource : SessionPreferencesDataSource {
    private val _bookmarkedSession = MutableStateFlow(emptySet<String>())
    override val bookmarkedSession: Flow<Set<String>> = _bookmarkedSession.filterNotNull()

    override suspend fun updateBookmarkedSession(bookmarkedSession: Set<String>) {
        _bookmarkedSession.value = bookmarkedSession.toSet()
    }
}
