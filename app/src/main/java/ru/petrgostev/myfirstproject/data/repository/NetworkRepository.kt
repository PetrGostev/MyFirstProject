package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ru.petrgostev.myfirstproject.ui.moviesList.padding.MoviesPagingSource
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.PageSize

class NetworkRepository(
    private val networkModule: NetworkModule,
) : NetworkRepositoryInterface {

    override suspend fun getImages(): ImagesResponse =
        networkModule.configurationApi.getConfiguration().images

    override suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    override  fun getMovies(sort: Category): LiveData<PagingData<MoviesItem>> {
        return Pager(
            config = PagingConfig(pageSize = PageSize.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(networkModule, sort) }
        ).liveData
    }

    override suspend fun getMovie(movieId: Int): MovieDetailsResponse =
        networkModule.moviesApi.getMovie(movieId = movieId)
}