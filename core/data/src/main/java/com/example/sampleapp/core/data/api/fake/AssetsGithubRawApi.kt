package com.example.sampleapp.core.data.api.fake

import android.content.Context
import com.example.sampleapp.core.data.api.GithubRawApi
import com.example.sampleapp.core.data.api.model.SessionResponse
import com.example.sampleapp.core.data.api.model.SponsorResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@OptIn(ExperimentalSerializationApi::class)
internal class AssetsGithubRawApi(
    context: Context,
    private val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    },
) : GithubRawApi {
    private val sponsors = context.assets.open("sponsors.json")
    private val sessions = context.assets.open("sessions.json")

    override suspend fun getSponsors(): List<SponsorResponse> {
        return json.decodeFromStream(sponsors)
    }

    override suspend fun getSessions(): List<SessionResponse> {
        return json.decodeFromStream(sessions)
    }
}
