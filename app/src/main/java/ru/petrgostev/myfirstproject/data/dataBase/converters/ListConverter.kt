package ru.petrgostev.myfirstproject.data.dataBase.converters

import androidx.room.TypeConverter

class ListConverter {

    @TypeConverter
    fun toListOfString(flatString: String) :List<String> = flatString.split(",")

    @TypeConverter
    fun fromListOfString(list: List<String>) : String = list.joinToString (",")
}