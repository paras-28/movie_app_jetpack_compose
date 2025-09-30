package com.example.movie_app.presentation.Home.HomeViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movie_app.BuildConfig
import com.example.movie_app.config.di.RetrofitInstance
import com.example.movie_app.domain.MovieResModel
import com.example.movie_app.domain.common.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import toNetworkError

@HiltViewModel
class HomeViewModel : ViewModel() {
    private val apiService = RetrofitInstance.apiService

    private val _popularMoviesResult = MutableLiveData<NetworkResponse<MovieResModel>>()
    val popularMoviesResult: LiveData<NetworkResponse<MovieResModel>> = _popularMoviesResult

    fun getData() {
        _popularMoviesResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response : retrofit2.Response<MovieResModel>  = apiService.getMovies(
                    authorization = "Bearer ${BuildConfig.API_KEY}",
                )
// Print response status code
                Log.d("HomeViewModel", "Response status code: ${response.code()}")
                if (response.isSuccessful) {
                    Log.i(
                        "getMovies",
                        "response ${response.body()}"
                    )
                    response.body()?.let {
                        _popularMoviesResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    Log.e(
                        "HomeViewModel",
                        "Error fetching popular movies: ${response.code()} - ${response.message()}"
                    )
                    _popularMoviesResult.value =
                        NetworkResponse.Error("Error: ${response.}")

                }
            } catch (e: Exception) {
                Log.e(
                    "HomeViewModel",
                    "Exception occurred while fetching popular movies: ${e.toString()}",
                    e
                )
                _popularMoviesResult.value = NetworkResponse.Error("Error: ${e.toNetworkError()}")
            }
        }
    }
}
