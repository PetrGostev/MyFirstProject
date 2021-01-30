package ru.petrgostev.myfirstproject.ui.moviesList.padding

import androidx.paging.PagingSource
import retrofit2.HttpException
import ru.petrgostev.myfirstproject.data.dataBase.entity.BaseMoviesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.PopularMoviesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.TopRatingMoviesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.UpcomingMoviesEntity
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesResponse
import ru.petrgostev.myfirstproject.data.repository.DataBaseRepositoryInterface
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.MoviesDate
import ru.petrgostev.myfirstproject.utils.Page
import java.io.IOException

class MoviesPagingSource(
    private val networkModule: NetworkModule,
    private val dataBaseRepository: DataBaseRepositoryInterface,
    private val sort: Category
) : PagingSource<Int, MoviesViewItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesViewItem> {
        val position = params.key ?: Page.STARTING_PAGE
        return try {
            val moviesEntitiesFromDataBase = dataBaseRepository.getMovies(sort)
            var moviesViewItemsFromDataBase: List<MoviesViewItem> = emptyList()
            var moviesViewItemsFromResponse: List<MoviesViewItem> = emptyList()
            val moviesResponse: MoviesResponse?
            val nextKey: Int?

            if (MoviesDate.IS_RELEVANT_UPDATE_DATE && position < 2 && moviesEntitiesFromDataBase.isNotEmpty()) {
                moviesViewItemsFromDataBase = createMoviesViewItemsFromDataBase(moviesEntitiesFromDataBase)
                nextKey = position + 1

            } else {
                moviesResponse = when (sort) {
                        Category.TOP_RATED -> networkModule.moviesApi.getAllTopRating(page = position)
                        Category.UPCOMING -> networkModule.moviesApi.getAllUpcoming(page = position)
                        else -> networkModule.moviesApi.getAllPopular(page = position)
                    }

               val moviesItemsFromResponse: List<MoviesItem> = moviesResponse.movieResponses

                moviesViewItemsFromResponse = createMoviesViewItemsFromResponse(moviesItemsFromResponse)

                if ((!MoviesDate.IS_RELEVANT_UPDATE_DATE && position < 2) || moviesEntitiesFromDataBase.isEmpty()) {
                    dataBaseRepository.deleteMovies(sort)
                    when (sort) {
                        Category.TOP_RATED -> dataBaseRepository.setTopRatingMovies(createTopRatingMoviesEntities(moviesItemsFromResponse))
                        Category.UPCOMING -> dataBaseRepository.setUpcomingMovies(createUpcomingMoviesEntities(moviesItemsFromResponse))
                        else ->  dataBaseRepository.setPopularMovies(createPopularMoviesEntities(moviesItemsFromResponse))
                    }
                }

                nextKey = if (moviesViewItemsFromResponse.isEmpty()) null else position + 1
            }

            LoadResult.Page(
                data = if (position < 2) moviesViewItemsFromDataBase else moviesViewItemsFromResponse,
                prevKey = if (position == Page.STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun createMoviesViewItemsFromDataBase(popularMovies: List<BaseMoviesEntity>): List<MoviesViewItem> {
        val moviesViewItems: MutableList<MoviesViewItem> = mutableListOf()
        for (moviesEntity in popularMovies) {
            moviesViewItems.add(
                MoviesViewItem(
                    id = moviesEntity.id.toInt(),
                    overview = moviesEntity.overview,
                    title = moviesEntity.title,
                    popularity = moviesEntity.popularity,
                    voteAverage = moviesEntity.voteAverage,
                    voteCount = moviesEntity.voteCount,
                    moviePoster = moviesEntity.moviePoster,
                    rating_5 = moviesEntity.rating_5,
                    minimumAge = moviesEntity.minimumAge,
                    genre = moviesEntity.genre
                )
            )
        }
        return moviesViewItems
    }

    private fun createMoviesViewItemsFromResponse(moviesItems: List<MoviesItem>): List<MoviesViewItem> {
        val moviesViewItems: MutableList<MoviesViewItem> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesViewItems.add(
                MoviesViewItem(
                    id = moviesItem.id,
                    overview = moviesItem.overview ?: "",
                    title = moviesItem.title ?: "",
                    popularity = moviesItem.popularity ?: 0.0,
                    voteAverage = moviesItem.voteAverage,
                    voteCount = moviesItem.voteCount ?: 0,
                    moviePoster = moviesItem.moviePoster,
                    rating_5 = moviesItem.rating_5,
                    minimumAge = moviesItem.minimumAge,
                    genre = moviesItem.getGenre()
                )
            )
        }
        return moviesViewItems
    }

    private fun createPopularMoviesEntities(moviesItems: List<MoviesItem>): List<PopularMoviesEntity> {
        val moviesEntities: MutableList<PopularMoviesEntity> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesEntities.add(
                PopularMoviesEntity(
                    id = moviesItem.id.toLong(),
                    overview = moviesItem.overview ?: "",
                    title = moviesItem.title ?: "",
                    popularity = moviesItem.popularity ?: 0.0,
                    voteAverage = moviesItem.voteAverage,
                    voteCount = moviesItem.voteCount ?: 0,
                    moviePoster = moviesItem.moviePoster,
                    rating_5 = moviesItem.rating_5,
                    minimumAge = moviesItem.minimumAge,
                    genre = moviesItem.getGenre()
                )
            )
        }
        return moviesEntities
    }

    private fun createTopRatingMoviesEntities(moviesItems: List<MoviesItem>): List<TopRatingMoviesEntity> {
        val moviesEntities: MutableList<TopRatingMoviesEntity> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesEntities.add(
                TopRatingMoviesEntity(
                    id = moviesItem.id.toLong(),
                    overview = moviesItem.overview ?: "",
                    title = moviesItem.title ?: "",
                    popularity = moviesItem.popularity ?: 0.0,
                    voteAverage = moviesItem.voteAverage,
                    voteCount = moviesItem.voteCount ?: 0,
                    moviePoster = moviesItem.moviePoster,
                    rating_5 = moviesItem.rating_5,
                    minimumAge = moviesItem.minimumAge,
                    genre = moviesItem.getGenre()
                )
            )
        }
        return moviesEntities
    }

    private fun createUpcomingMoviesEntities(moviesItems: List<MoviesItem>): List<UpcomingMoviesEntity> {
        val moviesEntities: MutableList<UpcomingMoviesEntity> = mutableListOf()

        for (moviesItem in moviesItems) {
            moviesEntities.add(
                UpcomingMoviesEntity(
                    id = moviesItem.id.toLong(),
                    overview = moviesItem.overview ?: "",
                    title = moviesItem.title ?: "",
                    popularity = moviesItem.popularity ?: 0.0,
                    voteAverage = moviesItem.voteAverage,
                    voteCount = moviesItem.voteCount ?: 0,
                    moviePoster = moviesItem.moviePoster,
                    rating_5 = moviesItem.rating_5,
                    minimumAge = moviesItem.minimumAge,
                    genre = moviesItem.getGenre()
                )
            )
        }
        return moviesEntities
    }
}