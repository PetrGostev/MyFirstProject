package ru.petrgostev.myfirstproject.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.network.pojo.MoviesResponse
import ru.petrgostev.myfirstproject.utils.ApiKey

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getAllPopular(
        @Query("api_key") apiKey: String = ApiKey.API_KEY,
        @Query("language") language: String = "ru",
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getAllTopRating(
        @Query("api_key") apiKey: String = ApiKey.API_KEY,
        @Query("language") language: String = "ru",
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getAllUpcoming(
        @Query("api_key") apiKey: String = ApiKey.API_KEY,
        @Query("language") language: String = "ru",
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int?,
        @Query("api_key") apiKey: String = ApiKey.API_KEY
    ): MovieDetailsResponse
}