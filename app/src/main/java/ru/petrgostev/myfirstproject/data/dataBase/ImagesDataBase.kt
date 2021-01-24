package ru.petrgostev.myfirstproject.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.petrgostev.myfirstproject.data.dataBase.converters.ListConverter
import ru.petrgostev.myfirstproject.data.dataBase.converters.TimeConverter
import ru.petrgostev.myfirstproject.data.dataBase.dao.ImagesDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.DateUpdateDao
import ru.petrgostev.myfirstproject.data.dataBase.dao.GenresDao
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.di.App

@Database(
    entities = [ImagesEntity::class, GenresEntity::class, DateUpdateEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(TimeConverter::class, ListConverter::class)
abstract class ImagesDataBase : RoomDatabase(){

    abstract fun configurationDao() : ImagesDao
    abstract fun genresDao() : GenresDao
    abstract fun dateUpdateDao() : DateUpdateDao

    companion object {
        private const val DB_NAME = "configurationDataBase"

        val INSTANCE: ImagesDataBase by lazy {
            Room.databaseBuilder(
                App.appContext,
                ImagesDataBase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}