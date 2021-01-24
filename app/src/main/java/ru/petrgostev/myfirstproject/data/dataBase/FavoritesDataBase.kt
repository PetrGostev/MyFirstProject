package ru.petrgostev.myfirstproject.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.petrgostev.myfirstproject.data.dataBase.dao.FavouritesDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.FavouritesEntity
import ru.petrgostev.myfirstproject.di.App

@Database(entities = [FavouritesEntity::class], exportSchema = false, version = 1 )
abstract class FavoritesDataBase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        private const val DB_NAME = "favoritesDataBase"

        val instance: FavoritesDataBase by lazy {
            Room.databaseBuilder(
                App.appContext,
                FavoritesDataBase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}