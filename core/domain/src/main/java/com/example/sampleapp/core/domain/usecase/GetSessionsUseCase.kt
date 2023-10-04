package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.SessionRepository
import com.example.sampleapp.core.model.Session
import javax.inject.Inject

class GetSessionsUseCase @Inject constructor(
    private val sessionRepository: SessionRepository,
) {
    suspend operator fun invoke(): List<Session> {
        return sessionRepository.getSessions()
    }
}
