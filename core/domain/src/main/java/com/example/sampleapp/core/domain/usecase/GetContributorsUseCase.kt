package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.ContributorRepository
import com.example.sampleapp.core.model.Contributor
import javax.inject.Inject

class GetContributorsUseCase @Inject constructor(
    private val repository: ContributorRepository,
) {
    suspend operator fun invoke(): List<Contributor> {
        return repository.getContributors(
            owner = OWNER,
            name = NAME,
        )
    }

    companion object {
        private const val OWNER = "droidknights"
        private const val NAME = "DroidKnights2023_App"
    }
}
