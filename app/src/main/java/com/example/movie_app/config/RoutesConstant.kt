package com.example.movie_app.config


import kotlinx.serialization.Serializable


sealed class Routes {

    @Serializable
    object LoginScreen

    @Serializable
    object HomeScreen

}
