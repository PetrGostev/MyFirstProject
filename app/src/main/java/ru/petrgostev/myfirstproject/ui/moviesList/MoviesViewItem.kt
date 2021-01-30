package ru.petrgostev.myfirstproject.ui.moviesList

data class MoviesViewItem(
    val id: Int,
    val overview: String,
    val title: String,
    val popularity: Double,
    val voteAverage: Double = 0.0,
    val voteCount: Int,
    val moviePoster:String,
    val rating_5: Float,
    val minimumAge: Int,
    val genre:String
)
