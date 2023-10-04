package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.SessionRepository
import javax.inject.Inject

class BookmarkSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
) {

    suspend operator fun invoke(sessionId: String, bookmark: Boolean) {
        return sessionRepository.bookmarkSession(sessionId, bookmark)
    }
}
