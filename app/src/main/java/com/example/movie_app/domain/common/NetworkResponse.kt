package com.example.movie_app.domain.common

import com.example.movie_app.data.AppException

/*sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}*/

sealed interface NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error(val exception: AppException, val errorCode: String? = null) :
        NetworkResponse<Nothing>

    data class Loading(val status: Boolean) : NetworkResponse<Nothing>

    data class Exception<T : Any>(val e: Throwable) : NetworkResponse<T>
}

