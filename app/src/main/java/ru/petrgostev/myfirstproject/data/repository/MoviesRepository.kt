package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ru.petrgostev.myfirstproject.data.dataBase.MoviesDataBase
import ru.petrgostev.myfirstproject.data.dataBase.dao.MoviesDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.ui.moviesList.padding.MoviesPagingSource
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.Page

class MoviesRepository(
    private val networkModule: NetworkModule,
    private val moviesDao: MoviesDao
) : IMoviesRepository {

    // DataBase
    override suspend fun getMoviesEntities(sort: Category): List<MoviesEntity> =
        when (sort) {
            Category.TOP_RATED -> moviesDao.getAll().filter { it.isTopRated }
                .toMutableList()
            Category.UPCOMING -> moviesDao.getAll().filter { it.isUpcoming }
                .toMutableList()
            else -> moviesDao.getAll().filter { it.isPopular }.toMutableList()
        }

    override suspend fun setMoviesEntities(movies: List<MoviesEntity>) {
        moviesDao.insertAll(movies)
    }

    override suspend fun deleteMoviesEntities() {
        moviesDao.clearAll()
    }

    //Network
    override suspend fun getMovie(movieId: Int): MovieDetailsResponse  =
        networkModule.moviesApi.getMovie(movieId = movieId)

    override suspend fun getMoviesFromNetwork(sort: Category, page: Int): List<MoviesItem> =
        when (sort) {
            Category.TOP_RATED -> networkModule.moviesApi.getAllTopRating(page = page).movieResponses
            Category.UPCOMING -> networkModule.moviesApi.getAllUpcoming(page = page).movieResponses
            else -> networkModule.moviesApi.getAllPopular(page = page).movieResponses
        }

    override fun getMovies(sort: Category): LiveData<PagingData<MoviesViewItem>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(this, sort) }
        ).liveData
    }
}