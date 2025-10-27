package com.example.movie_app.presentation.sharedViewModel

import androidx.lifecycle.ViewModel
import com.example.movie_app.data.MovieRepository
import com.example.movie_app.domain.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// SharedViewModel.kt
@HiltViewModel
class MovieSharedMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}