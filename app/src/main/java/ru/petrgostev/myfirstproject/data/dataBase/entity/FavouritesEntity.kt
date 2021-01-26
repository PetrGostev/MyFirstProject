package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
class FavouritesEntity(
    @PrimaryKey
    val id: Long,
    val movieId: Int
)