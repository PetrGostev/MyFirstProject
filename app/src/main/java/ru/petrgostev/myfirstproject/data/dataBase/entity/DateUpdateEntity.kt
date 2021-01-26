package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.petrgostev.myfirstproject.data.dataBase.converters.TimeConverter
import java.util.*

@Entity(tableName = "dateUpdate")
class DateUpdateEntity(
    @PrimaryKey
    val id: Long = 1,

    @TypeConverters(TimeConverter::class)
    val dateUpdate: Date
)