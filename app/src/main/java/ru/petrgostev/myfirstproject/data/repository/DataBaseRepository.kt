package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.MoviesDataBase
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.utils.Category

class DataBaseRepository : DataBaseRepositoryInterface {

    private val moviesDataBase = MoviesDataBase.INSTANCE

    override suspend fun getDateUpdateEntity(): DateUpdateEntity? =
        moviesDataBase.dateUpdateDao().getDate()

    override suspend fun setDateUpdateEntity(date: DateUpdateEntity) {
        moviesDataBase.dateUpdateDao().insertDate(date)
    }

    override suspend fun getImages(): ImagesEntity =
        moviesDataBase.configurationDao().getConfiguration()

    override suspend fun setImages(images: ImagesEntity) {
        moviesDataBase.configurationDao().insertConfiguration(images)
    }

    override suspend fun getGenres(): List<GenresEntity> =
        moviesDataBase.genresDao().getAll()

    override suspend fun setGenres(genres: List<GenresEntity>) {
        moviesDataBase.genresDao().insertAll(genres)
    }

    override suspend fun getFavourites(): List<FavouritesEntity>? =
        moviesDataBase.favouritesDao().getAll()

    override suspend fun setFavourite(favourite: FavouritesEntity) {
        moviesDataBase.favouritesDao().setFavorite(favourite)
    }

    override suspend fun getMovies(sort: Category): List<BaseMoviesEntity> =
        when (sort) {
            Category.TOP_RATED -> moviesDataBase.topRatingMoviesDao().getAll()
            Category.UPCOMING -> moviesDataBase.upcomingMoviesDao().getAll()
            else -> moviesDataBase.popularMoviesDao().getAll()
        }

    override suspend fun setPopularMovies(movies: List<PopularMoviesEntity>) {
        moviesDataBase.popularMoviesDao().insertAll(movies)
    }

    override suspend fun getTopRatingMovies(): List<TopRatingMoviesEntity> =
        moviesDataBase.topRatingMoviesDao().getAll()

    override suspend fun setTopRatingMovies(movies: List<TopRatingMoviesEntity>) {
        moviesDataBase.topRatingMoviesDao().insertAll(movies)
    }

    override suspend fun getUpcomingMovies(): List<UpcomingMoviesEntity> =
        moviesDataBase.upcomingMoviesDao().getAll()

    override suspend fun setUpcomingMovies(movies: List<UpcomingMoviesEntity>) {
        moviesDataBase.upcomingMoviesDao().insertAll(movies)
    }

    override suspend fun deleteMovies(sort: Category) {
        when (sort) {
            Category.TOP_RATED -> moviesDataBase.topRatingMoviesDao().clearAll()
            Category.UPCOMING -> moviesDataBase.upcomingMoviesDao().clearAll()
            else -> moviesDataBase.popularMoviesDao().clearAll()
        }
    }
}