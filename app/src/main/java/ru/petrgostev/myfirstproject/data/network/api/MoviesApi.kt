package ru.petrgostev.myfirstproject.data.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesResponse
import ru.petrgostev.myfirstproject.utils.Language

interface MoviesApi {

    @GET("movie/popular")
    suspend fun getAllPopular(
        @Query("language") language: String = Language.LANGUAGE_RU,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getAllTopRating(
        @Query("language") language: String = Language.LANGUAGE_RU,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getAllUpcoming(
        @Query("language") language: String = Language.LANGUAGE_RU,
        @Query("page") page: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String = Language.LANGUAGE_RU
    ): MovieDetailsResponse
}