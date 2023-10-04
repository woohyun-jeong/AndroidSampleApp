package com.example.sampleapp.core.data.api.fake

import com.example.sampleapp.core.data.api.model.ContributorResponse
import com.example.sampleapp.core.data.api.GithubApi

internal class FakeGithubApi(private val contributors: List<ContributorResponse>) : GithubApi {
    override suspend fun getContributors(owner: String, name: String): List<ContributorResponse> {
        return contributors
    }
}
