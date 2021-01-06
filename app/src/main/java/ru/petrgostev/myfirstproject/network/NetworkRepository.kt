package ru.petrgostev.myfirstproject.network

import ru.petrgostev.myfirstproject.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.network.pojo.MoviesResponse
import ru.petrgostev.myfirstproject.utils.Category

class NetworkRepository {
    private val networkModule = NetworkModule()

    suspend fun getImages(): ImagesResponse = networkModule.configurationApi.getConfiguration().images

    suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    suspend fun getMovies(page: Int, sort: Category): MoviesResponse =
        when (sort) {
            Category.TOP_RATED -> networkModule.moviesApi.getAllTopRating(page = page)
            Category.UPCOMING -> networkModule.moviesApi.getAllUpcoming(page = page)
            else -> networkModule.moviesApi.getAllPopular(page = page)
        }

    suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)
}