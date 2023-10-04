package com.example.sampleapp.core.data.mapper

import com.example.sampleapp.core.data.api.model.ContributorResponse
import com.example.sampleapp.core.model.Contributor

internal fun ContributorResponse.toData(): Contributor =
    Contributor(
        name = this.name,
        imageUrl = this.imageUrl,
        githubUrl = this.githubUrl,
    )
