package com.example.movie_app.data

open class AppException(message: String? = null, cause: Throwable? = null) :
    Throwable(message, cause)

class NetworkException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ServerException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ClientException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class UnknownException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)