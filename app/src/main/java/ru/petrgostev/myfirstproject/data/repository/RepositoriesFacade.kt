package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.di.App
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category

class RepositoriesFacade() : RepositoriesFacadeInterface {
    val dataBaseRepository: DataBaseRepositoryInterface by lazy { DataBaseRepository() }
    val networkRepository: NetworkRepositoryInterface by lazy { NetworkRepository(App.component.getNetworkModule(), dataBaseRepository) }

    //DataBase

    override suspend fun getDateUpdateEntity(): DateUpdateEntity? = dataBaseRepository.getDateUpdateEntity()

    override suspend fun setDateUpdateEntity(date: DateUpdateEntity) {
        dataBaseRepository.setDateUpdateEntity(date)
    }

    override suspend fun getImagesEntity(): ImagesEntity? = dataBaseRepository.getImages()

    override suspend fun setImagesEntity(images: ImagesEntity) {
        dataBaseRepository.setImages(images)
    }

    override suspend fun getGenresEntities(): List<GenresEntity>? = dataBaseRepository.getGenres()

    override suspend fun setGenresEntities(genres: List<GenresEntity>) {
        dataBaseRepository.setGenres(genres)
    }

    override suspend fun getFavouritesEntities(): List<FavouritesEntity>? = dataBaseRepository.getFavourites()

    override suspend fun setFavouriteEntities(favourite: FavouritesEntity) {
        dataBaseRepository.setFavourite(favourite)
    }

    override suspend fun getMoviesEntities(sort: Category): List<BaseMoviesEntity> = dataBaseRepository.getMovies(sort)

    override suspend fun setPopularMoviesEntities(movies: List<PopularMoviesEntity>) {
        dataBaseRepository.setPopularMovies(movies)
    }

    override suspend fun getTopRatingMoviesEntities(): List<TopRatingMoviesEntity> = dataBaseRepository.getTopRatingMovies()

    override suspend fun setTopRatingMoviesEntities(movies: List<TopRatingMoviesEntity>) {
        dataBaseRepository.setTopRatingMovies(movies)
    }

    override suspend fun getUpcomingMoviesEntities(): List<UpcomingMoviesEntity> = dataBaseRepository.getUpcomingMovies()

    override suspend fun setUpcomingMoviesEntities(movies: List<UpcomingMoviesEntity>) {
        dataBaseRepository.setUpcomingMovies(movies)
    }

    override suspend fun deleteMoviesEntities(sort: Category) {
        dataBaseRepository.deleteMovies(sort)
    }

    //Network

    override suspend fun getMovie(movieId: Int): MovieDetailsResponse = networkRepository.getMovie(movieId)

    override suspend fun getGenres(): List<GenresItem> = networkRepository.getGenres()

    override suspend fun getImages(): ImagesResponse = networkRepository.getImages()

    override fun getMovies(sort: Category): LiveData<PagingData<MoviesViewItem>> = networkRepository.getMovies(sort)
}