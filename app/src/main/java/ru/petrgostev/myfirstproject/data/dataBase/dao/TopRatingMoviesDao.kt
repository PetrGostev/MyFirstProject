package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.TopRatingMoviesEntity

@Dao
interface TopRatingMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<TopRatingMoviesEntity>)

    @Query("SELECT * FROM topRatingMovies")
    suspend fun getAll(): List<TopRatingMoviesEntity>

    @Query("DELETE FROM topRatingMovies")
    suspend fun clearAll()
}