package com.example.movie_app.config


import com.example.movie_app.domain.Movie
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    object LoginScreen

    @Serializable
    object HomeScreen

/*
    @Serializable
    data class MovieDetailScreen( val movie: Movie) {
        companion object {
            fun fromMovie(movie: Movie): MovieDetailScreen {
                return MovieDetailScreen(movie)
            }
        }
    }
*/

    @Serializable
    data class MovieDetailScreen(
        val movie: Movie
    )
}
