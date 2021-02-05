package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.utils.Category

interface IMoviesRepository {
    suspend fun getMovie(movieId: Int): MovieDetailsResponse
    suspend fun getPageMovies(page: Int, category: Category): List<MoviesEntity>
    fun getMovies(category: Category): LiveData<PagingData<MoviesEntity>>
}