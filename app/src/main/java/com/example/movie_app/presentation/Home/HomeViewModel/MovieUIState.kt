package com.example.movie_app.presentation.Home.HomeViewModel

import com.example.movie_app.domain.MovieResModel

// UI State sealed class for better state management
sealed class MovieUiState {
    object Idle : MovieUiState()
    object Loading : MovieUiState()
    data class Success(val data: MovieResModel) : MovieUiState()
    data class Error(val message: String, val errorCode: String? = null) : MovieUiState()
}