package com.example.movie_app.presentation.Home.HomeViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.BuildConfig
import com.example.movie_app.data.MovieRepository
import com.example.movie_app.domain.MovieResModel
import com.example.movie_app.domain.common.NetworkResponse
import com.example.movie_app.presentation.Home.HomeViewModel.MovieUiState.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    // Using StateFlow instead of LiveData (more Compose-friendly)
    private val _popularMoviesState = MutableStateFlow<MovieUiState>(MovieUiState.Idle)
    val popularMoviesState: StateFlow<MovieUiState> = _popularMoviesState.asStateFlow()

    private val _popularMoviesResult = MutableLiveData<NetworkResponse<MovieResModel>>()
    val popularMoviesResult: LiveData<NetworkResponse<MovieResModel>> = _popularMoviesResult

    init {
        // Automatically load data when ViewModel is created
        getData()
    }

    fun getData() {
        //   approach with LiveData
//        _popularMoviesResult.value = NetworkResponse.Loading(true)
        viewModelScope.launch {
            // Set loading state
            _popularMoviesState.value = MovieUiState.Loading
            try {
                val result = movieRepository.getPopularMovies(
                    page = 1,
                    language = "en-US",
                    authorization = "Bearer ${BuildConfig.API_KEY}"
                )

                // approach with LiveData
//                _popularMoviesResult.value = result

                // Update UI state based on result
                _popularMoviesState.value = when (result) {
                    is NetworkResponse.Success -> {
                        Log.d(
                            "HomeViewModel",
                            "Successfully fetched ${result.data.results.size} movies"
                        )
                        MovieUiState.Success(result.data)
                    }

                    is NetworkResponse.Error -> {
                        Log.e("HomeViewModel", "Error fetching movies: ${result.exception.message}")
                        Error(
                            message = result.exception.message ?: "Unknown error occurred",
                            errorCode = result.errorCode
                        )
                    }

                    is NetworkResponse.Loading -> MovieUiState.Loading

                }

            } catch (e: Exception) {
                Log.e(
                    "HomeViewModel",
                    "Exception occurred while fetching popular movies: ${e.toString()}",
                    e
                )
                _popularMoviesResult.value = NetworkResponse.Error(
                    com.example.movie_app.data.UnknownException(e.message),
                    e.message
                )
            }
        }
    }
}

