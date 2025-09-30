package com.example.movie_app.data

import com.example.movie_app.BuildConfig
import com.example.movie_app.domain.MovieResModel
import com.example.movie_app.domain.common.NetworkResponse
import jakarta.inject.Inject

/*class AppRepoImpl @Inject constructor(private val appService: AppService) :
    AppRepo {
    override suspend fun getUsers(): Flow<List<User>> = flow {
        try{
            val result = appService.getUsers()}
        emit(Result.Success(userResponse.map { it.toUser() }))
        catch(){
            throw IOException("Failed to load users")
        }
    }

}*/

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {


    override suspend fun getPopularMovies(
        page: Int,
        language: String,
        authorization: String
    ): NetworkResponse<MovieResModel> {
        return try {
            val response = apiService.getMovies(
                authorization = "Bearer ${BuildConfig.API_KEY}",
                language = language,
                page = page
            )

            if (response.isSuccessful && response.body() != null) {
                NetworkResponse.Success(response.body()!!)
            } else {
                NetworkResponse.Error(
                    AppException("API Error: ${response.message()}"),
                    errorCode = response.code().toString()
                )
            }
        } catch (e: Exception) {
            NetworkResponse.Error(
                AppException("Exception: ${e.message}", e),
                errorCode = null
            )
        }
    }
}