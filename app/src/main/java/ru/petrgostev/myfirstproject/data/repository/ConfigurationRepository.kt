package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.dao.DateUpdateDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.GenresDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.ImagesDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.network.api.NetworkModule
import ru.petrgostev.myfirstproject.utils.GenresMap
import ru.petrgostev.myfirstproject.utils.ImagesBaseUrl
import ru.petrgostev.myfirstproject.utils.MoviesDate
import ru.petrgostev.myfirstproject.utils.PosterSizeList
import java.util.*

class ConfigurationRepository(
    private val networkModule: NetworkModule,
    private val dateUpdateDao: DateUpdateDao,
    private val imagesDao: ImagesDao,
    private val genresDao: GenresDao
) : IConfigurationRepository {

    override suspend fun checkUpdateDate() {
        val dataUpdate = dateUpdateDao.getDate()?.dateUpdate

        if (dataUpdate != null) {
            MoviesDate.IS_RELEVANT_UPDATE_DATE =
                MoviesDate.FORMAT_DATE.format(dataUpdate) == MoviesDate.FORMAT_DATE.format(Date())
        }

        if (MoviesDate.IS_RELEVANT_UPDATE_DATE) {
            loadImages()
            loadGenres()

            return
        }

        updateImages()
        updateGenres()
    }

    private suspend fun loadImages() {
        val imagesEntity = imagesDao.getImages()
        if (ImagesBaseUrl.IMAGES_BASE_URL.isEmpty()) {
            ImagesBaseUrl.IMAGES_BASE_URL = imagesEntity.secureBaseUrl
        }
        if (PosterSizeList.posterSizes.isEmpty()) {
            PosterSizeList.posterSizes = imagesEntity.posterSizes
        }
    }

    private suspend fun updateImages() {
        val imagesItem = networkModule.configurationApi.getConfiguration().images

        if (ImagesBaseUrl.IMAGES_BASE_URL.isEmpty()) {
            ImagesBaseUrl.IMAGES_BASE_URL = imagesItem.secureBaseUrl
        }
        if (PosterSizeList.posterSizes.isEmpty()) {
            PosterSizeList.posterSizes = imagesItem.posterSizes
        }

        imagesDao.insertImages(imagesItem.toImagesEntity())
    }

    private suspend fun loadGenres() {
        val genres: List<GenresEntity> = genresDao.getAll()
        for (genre in genres) {
            GenresMap.genres[genre.id.toInt()] = genre.name
        }
    }

    private suspend fun updateGenres() {
        val genresItems = networkModule.genreApi.getAll().genres
        val genresEntities = mutableListOf<GenresEntity>()
        for (genresItem in genresItems) {
            GenresMap.genres[genresItem.id] = genresItem.name
            genresEntities.add(genresItem.toGenresEntity())
        }
        genresDao.insertAll(genresEntities)
        dateUpdateDao.insertDate(DateUpdateEntity(id = 1, dateUpdate = Date()))
    }
}