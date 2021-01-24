package ru.petrgostev.myfirstproject.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity

@Dao
interface DateUpdateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDate(dateUpdate: DateUpdateEntity)

    @Query("SELECT * FROM dateUpdate")
    suspend fun getDate(): DateUpdateEntity?
}