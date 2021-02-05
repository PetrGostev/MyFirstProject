package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ru.petrgostev.myfirstproject.data.dataBase.dao.MoviesDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.ui.moviesList.padding.MoviesPagingSource
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.MoviesDate
import ru.petrgostev.myfirstproject.utils.Page

class MoviesRepository(
    private val networkModule: NetworkModule,
    private val moviesDao: MoviesDao
) : IMoviesRepository {

    override suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)

    override suspend fun getPageMovies(page: Int, category: Category): List<MoviesEntity> {
        var moviesEntities: List<MoviesEntity> = emptyList()
        if (page < Page.TWO_PAGE && MoviesDate.IS_RELEVANT_UPDATE_DATE) {
            moviesEntities =
                if (getPageMoviesFromDB(category).isNotEmpty()) getPageMoviesFromDB(category) else getPageMoviesFromNet(page, category)

        } else {
            moviesEntities = getPageMoviesFromNet(page, category)
        }
        return moviesEntities
    }

    override fun getMovies(category: Category): LiveData<PagingData<MoviesEntity>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(this, category) }
        ).liveData
    }

    private suspend fun getPageMoviesFromDB(category: Category): MutableList<MoviesEntity> {
        return when (category) {
            Category.TOP_RATED -> moviesDao.getAll().filter { it.isTopRated }.toMutableList()
            Category.UPCOMING -> moviesDao.getAll().filter { it.isUpcoming }.toMutableList()
            else -> moviesDao.getAll().filter { it.isPopular }.toMutableList()
        }
    }

    private suspend fun getPageMoviesFromNet(page: Int, category: Category): List<MoviesEntity> {
        if (!MoviesDate.IS_RELEVANT_UPDATE_DATE) {
            moviesDao.clearAll()
        }

        val moviesEntities = when (category) {
            Category.TOP_RATED -> toMoviesEntities(
                category = category,
                networkModule.moviesApi.getAllTopRating(page = page).movieResponses
            )
            Category.UPCOMING -> toMoviesEntities(
                category = category,
                networkModule.moviesApi.getAllUpcoming(page = page).movieResponses
            )
            else -> toMoviesEntities(
                category = category,
                networkModule.moviesApi.getAllPopular(page = page).movieResponses
            )
        }
        if (page < Page.TWO_PAGE) {
            moviesDao.insertAll(moviesEntities)
        }
        return moviesEntities
    }

    private fun toMoviesEntities(
        category: Category,
        moviesItems: List<MoviesItem>
    ): List<MoviesEntity> {
        val moviesEntities = mutableListOf<MoviesEntity>()

        for (moviesItem in moviesItems) {
            moviesEntities.add(moviesItem.toMoviesEntity(category))
        }

        return moviesEntities
    }
}