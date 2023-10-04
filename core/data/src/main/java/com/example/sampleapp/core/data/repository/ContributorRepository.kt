package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.model.Contributor

interface ContributorRepository {

    suspend fun getContributors(
        owner: String,
        name: String,
    ): List<Contributor>
}
