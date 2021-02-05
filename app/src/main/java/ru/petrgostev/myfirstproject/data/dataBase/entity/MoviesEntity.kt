package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesEntity(
    @PrimaryKey
    val id: Long,
    val overview: String,
    val title: String,
    val popularity: Double,
    val voteAverage: Double = 0.0,
    val voteCount: Int,
    val moviePoster: String,
    val rating_5: Float,
    val minimumAge: Int,
    val genre: String,
    val isPopular: Boolean,
    val isTopRated: Boolean,
    val isUpcoming: Boolean,
)
