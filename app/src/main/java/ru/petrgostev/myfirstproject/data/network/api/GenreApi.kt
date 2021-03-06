package ru.petrgostev.myfirstproject.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.petrgostev.myfirstproject.data.network.pojo.GenresResponse
import ru.petrgostev.myfirstproject.utils.Language

interface GenreApi {

    @GET("genre/movie/list")
    suspend fun getAll(@Query("language") language: String = Language.LANGUAGE_RU): GenresResponse
}