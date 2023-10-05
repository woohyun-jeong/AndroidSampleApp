package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.model.Movie
import com.example.sampleapp.core.model.Session
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getAllMovies(start:Int): Flow<Movie>

    suspend fun getKoreaMovies(start:Int): Flow<Movie>

    suspend fun getSearchMovies(
        query:String,
        start:Int = 1,
        genre:String,
        country:String,
        yearFrom:Int,
        yearTo:Int,
    ): Flow<Movie>

}
