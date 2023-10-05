package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.api.GithubRawApi
import com.example.sampleapp.core.data.api.MovieSearchApi
import com.example.sampleapp.core.data.mapper.toData
import com.example.sampleapp.core.model.Movie
import com.example.sampleapp.core.model.Sponsor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultMovieRepository @Inject constructor(
    private val movieSearchApi: MovieSearchApi
) : MovieRepository {

    override suspend fun getAllMovies(start: Int): Flow<Movie> {
        return flow {
            val response = movieSearchApi.getMovies(
                query = "",
                genre = null,
                country = null,
                yearFrom = 2022,
                yearTo = 2022,
                start = start
            )

            response.items.asSequence().forEach {
                emit(it.toData())
            }
        }
    }

    override suspend fun getKoreaMovies(start: Int): Flow<Movie> {
        return flow {
            val response = movieSearchApi.getMovies(
                query = "",
                genre = null,
                country = "KR",
                yearFrom = 2022,
                yearTo = 2022,
                start = start
            )

            response.items.asSequence().forEach {
                emit(it.toData())
            }
        }
    }

    override suspend fun getSearchMovies(
        query: String,
        start: Int,
        genre: String,
        country: String,
        yearFrom: Int,
        yearTo: Int
    ): Flow<Movie> {
        return flow {
            val response = movieSearchApi.getMovies(
                query = query,
                genre = genre,
                country = country,
                yearFrom = yearFrom,
                yearTo = yearTo,
                start = start
            )

            response.items.asSequence().forEach {
                emit(it.toData())
            }
        }
    }
}
