package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookmarkedSessionsUseCase @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val getBookmarkedSessionIdsUseCase: GetBookmarkedSessionIdsUseCase
) {

    suspend operator fun invoke(): Flow<List<Session>> {
        return flow {
            emit(getSessionsUseCase())
        }.combine(getBookmarkedSessionIdsUseCase()) { allSession, bookmarkedSessions ->
            allSession
                .filter { session -> bookmarkedSessions.contains(session.id) }
                .sortedBy { it.startTime }
        }
    }
}
