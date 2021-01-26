package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
class GenresEntity(
    @PrimaryKey
    val id: Long,
    val name: String
)