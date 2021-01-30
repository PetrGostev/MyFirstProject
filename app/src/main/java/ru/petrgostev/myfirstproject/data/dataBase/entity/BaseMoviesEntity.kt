package ru.petrgostev.myfirstproject.data.dataBase.entity

abstract class BaseMoviesEntity {
    abstract val genre: String
    abstract val minimumAge: Int
    abstract val rating_5: Float
    abstract val moviePoster: String
    abstract val voteCount: Int
    abstract val voteAverage: Double
    abstract val popularity: Double
    abstract val title: String
    abstract val overview: String
    abstract val id: Long
}