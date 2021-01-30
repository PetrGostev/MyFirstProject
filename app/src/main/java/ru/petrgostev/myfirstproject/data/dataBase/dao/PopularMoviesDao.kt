package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.PopularMoviesEntity

@Dao
interface PopularMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<PopularMoviesEntity>)

    @Query("SELECT * FROM popularMovies")
    suspend fun getAll(): List<PopularMoviesEntity>

    @Query("DELETE FROM popularMovies")
    suspend fun clearAll()
}