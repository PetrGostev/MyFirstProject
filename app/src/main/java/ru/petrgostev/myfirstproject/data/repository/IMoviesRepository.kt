package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category

interface IMoviesRepository {

    // DataBase
    suspend fun getMoviesEntities(sort: Category): List<MoviesEntity>
    suspend fun setMoviesEntities(movies: List<MoviesEntity>)
    suspend fun deleteMoviesEntities()

    //Network
    suspend fun getMovie(movieId: Int): MovieDetailsResponse
    suspend fun getMoviesFromNetwork(sort: Category, page: Int): List<MoviesItem>
    fun getMovies(sort: Category): LiveData<PagingData<MoviesViewItem>>
}