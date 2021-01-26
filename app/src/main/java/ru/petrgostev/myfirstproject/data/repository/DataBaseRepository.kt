package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.data.dataBase.ImagesDataBase
import ru.petrgostev.myfirstproject.data.dataBase.FavoritesDataBase
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.FavouritesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity

class DataBaseRepository : DataBaseRepositoryInterface {

    private val configurationDataBase = ImagesDataBase.INSTANCE
    private val favoritesDataBase = FavoritesDataBase.instance

    override suspend fun getDateUpdateEntity(): DateUpdateEntity? =
        configurationDataBase.dateUpdateDao().getDate()

    override suspend fun setDateUpdateEntity(date: DateUpdateEntity) {
        configurationDataBase.dateUpdateDao().insertDate(date)
    }

    override suspend fun getImages(): ImagesEntity? =
        configurationDataBase.configurationDao().getConfiguration()

    override suspend fun setImages(images: ImagesEntity) {
        configurationDataBase.configurationDao().insertConfiguration(images)
    }

    override suspend fun getGenres(): List<GenresEntity>? =
        configurationDataBase.genresDao().getAll()


    override suspend fun setGenres(genres: List<GenresEntity>) {
        configurationDataBase.genresDao().insertAll(genres)
    }

    override suspend fun getFavourites(): List<FavouritesEntity>? =
        favoritesDataBase.favouritesDao().getAll()


    override suspend fun setFavourite(favourite: FavouritesEntity) {
        favoritesDataBase.favouritesDao().setFavorite(favourite)
    }
}