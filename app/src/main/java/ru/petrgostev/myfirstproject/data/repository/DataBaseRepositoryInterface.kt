package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.FavouritesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity

interface DataBaseRepositoryInterface {
    suspend fun getDateUpdateEntity() : DateUpdateEntity?
    suspend fun setDateUpdateEntity(date: DateUpdateEntity)
    suspend fun getImages(): ImagesEntity?
    suspend fun setImages(images: ImagesEntity)
    suspend fun getGenres(): List<GenresEntity>?
    suspend fun setGenres(genres:List<GenresEntity>)
    suspend fun getFavourites(): List<FavouritesEntity>?
    suspend fun setFavourite(favourite: FavouritesEntity)
}