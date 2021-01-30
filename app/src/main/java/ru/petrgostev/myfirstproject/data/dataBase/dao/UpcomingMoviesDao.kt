package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.UpcomingMoviesEntity

@Dao
interface UpcomingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<UpcomingMoviesEntity>)

    @Query("SELECT * FROM upcomingMovies")
    suspend fun getAll(): List<UpcomingMoviesEntity>

    @Query("DELETE FROM upcomingMovies")
    suspend fun clearAll()
}