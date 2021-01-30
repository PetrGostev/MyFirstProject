package ru.petrgostev.myfirstproject.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcomingMovies")
class UpcomingMoviesEntity (
    @PrimaryKey
    override val id: Long,
    override val overview: String,
    override val title: String,
    override val popularity: Double,
    override val voteAverage: Double = 0.0,
    override val voteCount: Int,
    override val moviePoster: String,
    override val rating_5: Float,
    override val minimumAge: Int,
    override val genre: String
) : BaseMoviesEntity()