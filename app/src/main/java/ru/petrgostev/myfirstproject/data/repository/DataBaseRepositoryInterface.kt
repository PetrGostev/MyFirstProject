package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.utils.Category

interface DataBaseRepositoryInterface {
    suspend fun getDateUpdateEntity() : DateUpdateEntity?
    suspend fun setDateUpdateEntity(date: DateUpdateEntity)
    suspend fun getImages(): ImagesEntity?
    suspend fun setImages(images: ImagesEntity)
    suspend fun getGenres(): List<GenresEntity>?
    suspend fun setGenres(genres:List<GenresEntity>)
    suspend fun getFavourites(): List<FavouritesEntity>?
    suspend fun setFavourite(favourite: FavouritesEntity)
    suspend fun getMovies(sort: Category): List<BaseMoviesEntity>
    suspend fun setPopularMovies(movies: List<PopularMoviesEntity>)
    suspend fun getTopRatingMovies(): List<TopRatingMoviesEntity>
    suspend fun setTopRatingMovies(movies: List<TopRatingMoviesEntity>)
    suspend fun getUpcomingMovies(): List<UpcomingMoviesEntity>
    suspend fun setUpcomingMovies(movies: List<UpcomingMoviesEntity>)
    suspend fun deleteMovies(sort:Category)
}