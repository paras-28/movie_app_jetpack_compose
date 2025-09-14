package com.example.movie_app.data


import com.example.movie_app.domain.MovieResModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AdviceApiService {

    @GET("3/movie/popular")
    suspend fun getMovies(
        @Header("Authorization") authorization: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Query(value = "language") language: String = "en-US",
        @Query(value = "page") page: Int = 1
    ): Response<MovieResModel>
}