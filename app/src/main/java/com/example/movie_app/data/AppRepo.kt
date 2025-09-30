package com.example.movie_app.data

import com.example.movie_app.domain.MovieResModel
import com.example.movie_app.domain.common.NetworkResponse

interface MovieRepository {
    suspend fun getPopularMovies(
        page: Int = 1,
        language: String = "en-US"
    ): NetworkResponse<MovieResModel>
}
