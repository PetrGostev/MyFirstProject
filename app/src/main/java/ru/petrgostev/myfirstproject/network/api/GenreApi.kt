package ru.petrgostev.myfirstproject.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.petrgostev.myfirstproject.network.pojo.GenresResponse
import ru.petrgostev.myfirstproject.utils.ApiKey

interface GenreApi {
    @GET("genre/movie/list")
    suspend fun getAll(
        @Query("api_key") api_key: String = ApiKey.API_KEY,
        @Query("language") language: String = "ru"
    ): GenresResponse
}