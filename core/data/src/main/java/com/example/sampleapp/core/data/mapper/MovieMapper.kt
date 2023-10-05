package com.example.sampleapp.core.data.mapper

import com.example.sampleapp.core.data.api.model.ContributorResponse
import com.example.sampleapp.core.data.api.model.MovieItemResponse
import com.example.sampleapp.core.data.api.model.MovieSearchResponse
import com.example.sampleapp.core.model.Contributor
import com.example.sampleapp.core.model.Movie

internal fun MovieItemResponse.toData(): Movie =
    Movie(
        title = this.title,
        linkUrl = this.link,
        imageUrl = this.image,
        userRating = this.userRating
    )
