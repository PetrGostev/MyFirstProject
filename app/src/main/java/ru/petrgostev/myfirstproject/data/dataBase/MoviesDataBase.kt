package ru.petrgostev.myfirstproject.data.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.petrgostev.myfirstproject.data.dataBase.converters.ListConverter
import ru.petrgostev.myfirstproject.data.dataBase.converters.TimeConverter
import ru.petrgostev.myfirstproject.data.dataBase.dao.*
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.di.App

@Database(
    entities = [ImagesEntity::class,
        GenresEntity::class, DateUpdateEntity::class,
        MoviesEntity::class],
    exportSchema = false,
    version = 3
)
@TypeConverters(TimeConverter::class, ListConverter::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao
    abstract fun genresDao(): GenresDao
    abstract fun dateUpdateDao(): DateUpdateDao
    abstract fun moviesDao(): MoviesDao

    companion object {
        private const val DB_NAME = "moviesDataBase"

        val INSTANCE: MoviesDataBase by lazy {
            Room.databaseBuilder(
                App.appContext,
                MoviesDataBase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}