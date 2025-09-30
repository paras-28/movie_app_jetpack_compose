package com.example.movie_app.presentation.util

import com.example.movie_app.data.AppException
import com.example.movie_app.data.ClientException
import com.example.movie_app.data.Constant
import com.example.movie_app.data.NetworkException
import com.example.movie_app.data.ServerException
import com.example.movie_app.data.UnknownException
import com.example.movie_app.domain.common.NetworkResponse
import kotlinx.coroutines.flow.Flow
import okio.IOException
import retrofit2.HttpException


fun <T> Flow<T>.safeFlow(): Flow<NetworkResponse<T>> {
    return this
        .map<T, NetworkResponse<T>> {
            NetworkResponse.Success(it)
        }
        .onStart { emit(NetworkResponse.Loading(true)) }
        .onCompletion { emit(NetworkResponse.Loading(false)) }
        .catch { error ->
            val exception = when (error) {
                is HttpException -> {
                    when (error.code()) {
                        in 400..499 -> {
                            ClientException(
                                message = "${Constant.CLIENT_ERROR}: ${error.code()}",
                                cause = error,
                            )
                        }

                        in 500..599 -> {
                            ServerException(
                                message = "${Constant.SERVER_ERROR}: ${error.code()}",
                                cause = error
                            )
                        }

                        else -> {
                            UnknownException(
                                message = "${Constant.HTTP_UNKNOWN_ERROR}: ${error.code()}",
                                cause = error
                            )
                        }
                    }
                }

                is IOException -> NetworkException(
                    message = Constant.NETWORK_ERROR,
                    cause = error
                )

                else -> AppException(
                    message = Constant.UNKNOWN_ERROR,
                    cause = error
                )
            }

            val errorCode = when (error) {
                is HttpException -> {
                    when (error.code()) {
                        in 400..499 -> {
                            "#ER${error.code()}"
                        }

                        in 500..599 -> {
                            "#ER${error.code()}"
                        }

                        else -> {
                            "#ER${error.code()}"
                        }
                    }
                }

                else -> {
                    error.cause?.message.toString()
                }
            }
            emit(NetworkResponse.Error(exception, errorCode))
        }
}