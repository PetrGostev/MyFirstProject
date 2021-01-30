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
        FavouritesEntity::class,
        PopularMoviesEntity::class,
        TopRatingMoviesEntity::class,
        UpcomingMoviesEntity::class],
    exportSchema = false,
    version = 2
)
@TypeConverters(TimeConverter::class, ListConverter::class)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun configurationDao(): ImagesDao
    abstract fun genresDao(): GenresDao
    abstract fun dateUpdateDao(): DateUpdateDao
    abstract fun favouritesDao(): FavouritesDao
    abstract fun popularMoviesDao(): PopularMoviesDao
    abstract fun topRatingMoviesDao(): TopRatingMoviesDao
    abstract fun upcomingMoviesDao(): UpcomingMoviesDao

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