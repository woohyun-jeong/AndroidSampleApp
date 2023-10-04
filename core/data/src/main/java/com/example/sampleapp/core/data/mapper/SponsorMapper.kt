package com.example.sampleapp.core.data.mapper

import com.example.sampleapp.core.data.api.model.SponsorResponse
import com.example.sampleapp.core.model.Sponsor

internal fun SponsorResponse.toData(): Sponsor = Sponsor(
    name = name,
    imageUrl = imageUrl,
    homepage = homepage,
    grade = when (grade) {
        SponsorResponse.Grade.PLATINUM -> Sponsor.Grade.PLATINUM
        SponsorResponse.Grade.GOLD -> Sponsor.Grade.GOLD
    }
)
