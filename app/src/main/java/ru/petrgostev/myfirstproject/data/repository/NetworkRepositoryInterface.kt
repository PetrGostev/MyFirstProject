package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category

interface NetworkRepositoryInterface {

    suspend fun getMovie(movieId: Int): MovieDetailsResponse

    suspend fun getGenres(): List<GenresItem>

    suspend fun getImages(): ImagesResponse

    fun getMovies(sort: Category): LiveData<PagingData<MoviesViewItem>>
}