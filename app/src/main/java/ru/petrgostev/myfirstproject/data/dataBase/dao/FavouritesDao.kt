package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.FavouritesEntity

@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<FavouritesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFavorite(favourite: FavouritesEntity);

    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<FavouritesEntity>?
}