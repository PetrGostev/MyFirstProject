package ru.petrgostev.myfirstproject.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.petrgostev.myfirstproject.moviesList.padding.MoviesPagingSource
import ru.petrgostev.myfirstproject.network.api.NetworkModule
import ru.petrgostev.myfirstproject.network.pojo.*
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.PageSize

class NetworkRepository(private val networkModule: NetworkModule) : NetworkRepositoryInterface {

    override suspend fun getImages(): ImagesResponse = networkModule.configurationApi.getConfiguration().images

    override suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    override fun getMovies(sort: Category): Flow<PagingData<MoviesItem>> {
        return Pager(
            config = PagingConfig(pageSize = PageSize.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(networkModule, sort) }
        ).flow
    }

    override suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)
}