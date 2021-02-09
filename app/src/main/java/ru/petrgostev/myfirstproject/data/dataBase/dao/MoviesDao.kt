package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<MoviesEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MoviesEntity>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}