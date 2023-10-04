package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.ContributorRepository
import com.example.sampleapp.core.model.Contributor

internal class FakeContributorRepository(
    private val contributors: List<Contributor>,
) : ContributorRepository {
    override suspend fun getContributors(owner: String, name: String): List<Contributor> {
        return contributors
    }
}
