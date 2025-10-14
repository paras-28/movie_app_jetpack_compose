package com.example.movie_app.data

import com.example.movie_app.domain.common.NetworkResponse
import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> retrofit2.Response<T>): NetworkResponse<T> {
    return try {
        NetworkResponse.Loading(true)
        val response = apiCall.invoke()
        if (response.isSuccessful && response.body() != null) {

            NetworkResponse.Success(response.body()!!)
        } else {
            NetworkResponse.Error(
                ClientException("Something went wrong"),
                errorCode = "#ER${response.code()}"
            );
        }


    } catch (error: Throwable) {
        println("error: ${error}");
        val exception = when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 400..499 -> {
                        ClientException(
                            message = "${Constant.CLIENT_ERROR}: ${error.code()}",
                            cause = error,
                        )
                    }

                    in 500..599 -> ServerException(
                        message = "${Constant.SERVER_ERROR}: ${error.code()}",
                        cause = error
                    )

                    else -> UnknownException(
                        message = "${Constant.HTTP_UNKNOWN_ERROR}: ${error.code()}",
                        cause = error
                    )
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
        NetworkResponse.Error(exception, errorCode)
    } finally {
        NetworkResponse.Loading(false)
    }
}

/**
 * Extension function for Retrofit Response to safely extract data
 */
fun <T> retrofit2.Response<T>.toNetworkResponse(): NetworkResponse<T> {
    return if (this.isSuccessful && this.body() != null) {
        NetworkResponse.Success(this.body()!!)
    } else {
        NetworkResponse.Error(
            ClientException("API Error: ${this.message()}"),
            errorCode = "#ER${this.code()}"
        )
    }
}