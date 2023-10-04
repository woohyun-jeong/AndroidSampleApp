package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.model.Sponsor

interface SponsorRepository {

    suspend fun getSponsors(): List<Sponsor>
}
