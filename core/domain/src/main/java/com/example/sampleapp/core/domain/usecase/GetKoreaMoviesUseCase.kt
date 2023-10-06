package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.MovieRepository
import com.example.sampleapp.core.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKoreaMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
)   {

    suspend operator fun invoke(start:Int): Flow<Movie> {
        return movieRepository.getKoreaMovies(start)
    }

}