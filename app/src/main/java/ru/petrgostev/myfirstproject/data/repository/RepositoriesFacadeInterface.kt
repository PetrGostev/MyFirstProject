package ru.petrgostev.myfirstproject.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.Category

interface RepositoriesFacadeInterface {
    //DataBase
    suspend fun getDateUpdateEntity() : DateUpdateEntity?
    suspend fun setDateUpdateEntity(date: DateUpdateEntity)
    suspend fun getImagesEntity(): ImagesEntity?
    suspend fun setImagesEntity(images: ImagesEntity)
    suspend fun getGenresEntities(): List<GenresEntity>?
    suspend fun setGenresEntities(genres:List<GenresEntity>)
    suspend fun getFavouritesEntities(): List<FavouritesEntity>?
    suspend fun setFavouriteEntities(favourite: FavouritesEntity)
    suspend fun getMoviesEntities(sort: Category): List<BaseMoviesEntity>
    suspend fun setPopularMoviesEntities(movies: List<PopularMoviesEntity>)
    suspend fun getTopRatingMoviesEntities(): List<TopRatingMoviesEntity>
    suspend fun setTopRatingMoviesEntities(movies: List<TopRatingMoviesEntity>)
    suspend fun getUpcomingMoviesEntities(): List<UpcomingMoviesEntity>
    suspend fun setUpcomingMoviesEntities(movies: List<UpcomingMoviesEntity>)
    suspend fun deleteMoviesEntities(sort: Category)

    //Network
    suspend fun getMovie(movieId: Int): MovieDetailsResponse
    suspend fun getGenres(): List<GenresItem>
    suspend fun getImages(): ImagesResponse
    fun getMovies(sort: Category): LiveData<PagingData<MoviesViewItem>>
}