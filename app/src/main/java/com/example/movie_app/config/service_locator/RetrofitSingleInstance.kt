package com.example.movie_app.config.service_locator





import com.example.movie_app.data.AdviceApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val baseUrl = "https://api.themoviedb.org/"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: AdviceApiService = getInstance().create(AdviceApiService::class.java)
}