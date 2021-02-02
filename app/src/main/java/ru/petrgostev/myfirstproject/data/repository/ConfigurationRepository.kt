package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.dao.DateUpdateDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.GenresDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.ImagesDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.ImagesResponse

class ConfigurationRepository(
    private val networkModule: NetworkModule,
    private val dateUpdateDao: DateUpdateDao,
    private val imagesDao: ImagesDao,
    private val genresDao: GenresDao
) : IConfigurationRepository {

    //DataBase
    override suspend fun getDateUpdateEntity(): DateUpdateEntity? =
        dateUpdateDao.getDate()

    override suspend fun setDateUpdateEntity(date: DateUpdateEntity) {
        dateUpdateDao.insertDate(date)
    }

    override suspend fun getImagesEntity(): ImagesEntity = imagesDao.getImages()

    override suspend fun setImagesEntity(images: ImagesEntity) {
        imagesDao.insertImages(images)
    }

    override suspend fun getGenresEntities(): List<GenresEntity> = genresDao.getAll()

    override suspend fun setGenresEntities(genres: List<GenresEntity>) {
        genresDao.insertAll(genres)
    }

    //Network
    override suspend fun getGenres(): List<GenresItem> = networkModule.genreApi.getAll().genres

    override suspend fun getImages(): ImagesResponse =
        networkModule.configurationApi.getConfiguration().images
}