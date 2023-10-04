package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun getSessions(): List<Session>

    suspend fun getSession(sessionId: String): Session

    suspend fun getBookmarkedSessionIds(): Flow<Set<String>>

    suspend fun bookmarkSession(sessionId: String, bookmark: Boolean)
}
