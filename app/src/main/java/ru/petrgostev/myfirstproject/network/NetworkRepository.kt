package ru.petrgostev.myfirstproject.network

import ru.petrgostev.myfirstproject.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.network.pojo.MoviesResponse

class NetworkRepository {
    private val networkModule = NetworkModule()

    suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    suspend fun getMovies(page: Int, sort: Int): MoviesResponse =
        when (sort) {
            1 -> networkModule.moviesApi.getAllTopRating(page = page)
            2 -> networkModule.moviesApi.getAllUpcoming(page = page)
            else -> networkModule.moviesApi.getAllPopular(page = page)
        }

    suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)
}