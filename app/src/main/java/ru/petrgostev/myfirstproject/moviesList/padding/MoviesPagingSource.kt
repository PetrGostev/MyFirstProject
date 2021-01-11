package ru.petrgostev.myfirstproject.moviesList.padding

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.petrgostev.myfirstproject.network.NetworkRepository
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.network.pojo.MoviesResponse
import ru.petrgostev.myfirstproject.utils.Category
import java.io.IOException

private const val STARTING_PAGE = 1

class MoviesPagingSource(
    private val networkRepository: NetworkRepository,
    private val sort: Category
) : PagingSource<Int, MoviesItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesItem> {
        val position = params.key ?: STARTING_PAGE
        return try {
            val moviesResponse =
            when (sort) {
            Category.TOP_RATED -> networkRepository.networkModule.moviesApi.getAllTopRating(page = position)
            Category.UPCOMING -> networkRepository.networkModule.moviesApi.getAllUpcoming(page = position)
            else -> networkRepository.networkModule.moviesApi.getAllPopular(page = position)
        }
            val movies:List<MoviesItem> = moviesResponse.movieResponses
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}