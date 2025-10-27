package com.example.movie_app.config


import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    object LoginScreen

    @Serializable
    object HomeScreen

    /*    @Serializable
        data class MovieDetailScreen(@Contextual val movie: Movie) {
            companion object {
                fun fromMovie(movie: Movie): MovieDetailScreen {
                    return MovieDetailScreen(movie)
                }
            }
        }*/

    @Serializable
    object MovieDetailScreen
}
