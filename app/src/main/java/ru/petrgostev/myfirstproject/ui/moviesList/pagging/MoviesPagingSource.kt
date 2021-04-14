package ru.petrgostev.myfirstproject.ui.moviesList.padding

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.repository.MoviesRepository
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.Page
import java.io.IOException

class MoviesPagingSource(
    private val moviesRepository: MoviesRepository,
    private val category: Category
) : PagingSource<Int, MoviesEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesEntity> {
        val position = params.key ?: Page.STARTING_PAGE
        return try {
            val moviesEntities = moviesRepository.getPageMovies(position,category)

            LoadResult.Page(
                data = moviesEntities,
                prevKey = if (position == Page.STARTING_PAGE) null else position - 1,
                nextKey = if (moviesEntities.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}