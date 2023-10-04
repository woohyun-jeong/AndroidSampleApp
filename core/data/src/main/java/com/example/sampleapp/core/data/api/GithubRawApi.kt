package com.example.sampleapp.core.data.api

import com.example.sampleapp.core.data.api.model.SessionResponse
import com.example.sampleapp.core.data.api.model.SponsorResponse
import retrofit2.http.GET

internal interface GithubRawApi {

    @GET("/droidknights/DroidKnights2023_App/main/core/data/src/main/assets/sponsors.json")
    suspend fun getSponsors(): List<SponsorResponse>

    @GET("/droidknights/DroidKnights2023_App/main/core/data/src/main/assets/sessions.json")
    suspend fun getSessions(): List<SessionResponse>
}
