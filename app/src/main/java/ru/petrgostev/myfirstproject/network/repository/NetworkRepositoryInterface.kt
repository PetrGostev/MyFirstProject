package ru.petrgostev.myfirstproject.network.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.Category

interface NetworkRepositoryInterface {

    suspend fun getMovie(movieId: Int): MovieDetailsResponse

    suspend fun getGenres(): List<GenresItem>

    suspend fun getImages(): ImagesResponse

    fun getMovies(sort: Category): LiveData<PagingData<MoviesItem>>
}