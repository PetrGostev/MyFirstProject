package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse

interface IConfigurationRepository {

    //DataBase
    suspend fun getDateUpdateEntity() : DateUpdateEntity?
    suspend fun setDateUpdateEntity(date: DateUpdateEntity)
    suspend fun getImagesEntity(): ImagesEntity?
    suspend fun setImagesEntity(images: ImagesEntity)
    suspend fun getGenresEntities(): List<GenresEntity>?
    suspend fun setGenresEntities(genres:List<GenresEntity>)

    //Network
    suspend fun getGenres(): List<GenresItem>
    suspend fun getImages(): ImagesResponse
}