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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    private val _popularMoviesResult = MutableLiveData<NetworkResponse<MovieResModel>>()
    val popularMoviesResult: LiveData<NetworkResponse<MovieResModel>> = _popularMoviesResult

    fun getData() {
        _popularMoviesResult.value = NetworkResponse.Loading(true)
        viewModelScope.launch {
            try {
                val result = movieRepository.getPopularMovies(
                    page = 1,
                    language = "en-US",
                    authorization = "Bearer ${BuildConfig.API_KEY}"
                )
                _popularMoviesResult.value = result
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
