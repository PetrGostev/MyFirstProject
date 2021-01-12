package ru.petrgostev.myfirstproject.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.petrgostev.myfirstproject.di.App
import ru.petrgostev.myfirstproject.moviesList.padding.MoviesPagingSource
import ru.petrgostev.myfirstproject.network.pojo.*
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.PageSize

class NetworkRepository {
    val networkModule = App.component.getNetworkModule()

    suspend fun getImages(): ImagesResponse =
        networkModule.configurationApi.getConfiguration().images

    suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)

    fun getMovies(sort: Category): Flow<PagingData<MoviesItem>> {
        return Pager(
            config = PagingConfig(pageSize = PageSize.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(this, sort) }
        ).flow
    }
}