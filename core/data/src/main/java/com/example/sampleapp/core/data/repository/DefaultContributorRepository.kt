package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.api.GithubApi
import com.example.sampleapp.core.data.mapper.toData
import com.example.sampleapp.core.model.Contributor
import javax.inject.Inject

internal class DefaultContributorRepository @Inject constructor(
    private val githubApi: GithubApi,
) : ContributorRepository {

    override suspend fun getContributors(
        owner: String,
        name: String,
    ): List<Contributor> {
        return githubApi.getContributors(owner, name)
            .map { it.toData() }
    }
}
