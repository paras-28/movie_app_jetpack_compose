package com.example.movie_app.presentation.Home.controller

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.BuildConfig
import com.example.movie_app.config.service_locator.RetrofitInstance
import com.example.movie_app.domain.MovieResModel
import com.example.movie_app.domain.common.NetworkResponse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val apiService = RetrofitInstance.apiService

    private val _popularMoviesResult = MutableLiveData<NetworkResponse<MovieResModel>>()
    val popularMoviesResult: LiveData<NetworkResponse<MovieResModel>> = _popularMoviesResult

    fun getData() {
        _popularMoviesResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = apiService.getMovies(
                    authorization = "Bearer ${BuildConfig.API_KEY}",
                )

                if (response.isSuccessful) {
                    Log.i(
                        "HomeViewModel",
                        "response ${response.body()}"
                    )
                    response.body()?.let {
                        _popularMoviesResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _popularMoviesResult.value =
                        NetworkResponse.Error("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(
                    "HomeViewModel",
                    "Exception occurred while fetching popular movies: ${e.toString()}",
                    e
                )
                _popularMoviesResult.value = NetworkResponse.Error("Error: ${e.message}")
            }
        }
    }
}
