package ru.petrgostev.myfirstproject.ui.moviesList.padding

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.data.repository.MoviesRepository
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.MoviesDate
import ru.petrgostev.myfirstproject.utils.Page
import java.io.IOException

class MoviesPagingSource(
    private val moviesRepository: MoviesRepository,
    private val sort: Category
) : PagingSource<Int, MoviesViewItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesViewItem> {
        val position = params.key ?: Page.STARTING_PAGE
        return try {
            val moviesEntitiesFromDataBase = if(position < Page.TWO_PAGE) moviesRepository.getMoviesEntities(sort)  else emptyList()
            var moviesViewItemsFromDataBase: List<MoviesViewItem> = emptyList()
            var moviesViewItemsFromResponse: List<MoviesViewItem> = emptyList()
            val nextKey: Int?

            if (MoviesDate.IS_RELEVANT_UPDATE_DATE && moviesEntitiesFromDataBase.isNotEmpty()) {
                moviesViewItemsFromDataBase = createMoviesViewItemsFromDataBase(moviesEntitiesFromDataBase)
                nextKey = position + 1

            } else {
                val moviesItemsFromResponse: List<MoviesItem> = moviesRepository.getMoviesFromNetwork(sort, position)

                moviesViewItemsFromResponse = createMoviesViewItemsFromResponse(moviesItemsFromResponse)
                nextKey = if (moviesViewItemsFromResponse.isEmpty()) null else position + 1

                if (!MoviesDate.IS_RELEVANT_UPDATE_DATE) {
                    moviesRepository.deleteMoviesEntities()
                }

                if (position < Page.TWO_PAGE) {
                    moviesRepository.setMoviesEntities(createMoviesEntities(moviesItemsFromResponse))
                }
            }

            LoadResult.Page(
                data = if (position < Page.TWO_PAGE) moviesViewItemsFromDataBase else moviesViewItemsFromResponse,
                prevKey = if (position == Page.STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun createMoviesViewItemsFromDataBase(movies: List<MoviesEntity>): List<MoviesViewItem> {
        val moviesViewItems: MutableList<MoviesViewItem> = mutableListOf()
        for (moviesEntity in movies) {
            moviesViewItems.add(moviesEntity.toMoviesViewItem())
        }
        return moviesViewItems
    }

    private fun createMoviesViewItemsFromResponse(moviesItems: List<MoviesItem>): List<MoviesViewItem> {
        val moviesViewItems: MutableList<MoviesViewItem> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesViewItems.add(moviesItem.toMoviesViewItem())
        }
        return moviesViewItems
    }

    private fun createMoviesEntities(moviesItems: List<MoviesItem>): List<MoviesEntity> {
        val moviesEntities: MutableList<MoviesEntity> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesEntities.add(moviesItem.toMoviesEntity(sort))
        }
        return moviesEntities
    }
}