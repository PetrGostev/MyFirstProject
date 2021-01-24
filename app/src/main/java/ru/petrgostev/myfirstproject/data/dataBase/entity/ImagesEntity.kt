package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.petrgostev.myfirstproject.data.dataBase.converters.ListConverter

@Entity(tableName = "images")
class ImagesEntity (
    @PrimaryKey
    val id:Long = 1,

    @TypeConverters(ListConverter::class)
    val posterSizes: List<String> = emptyList(),

    val secureBaseUrl: String
)