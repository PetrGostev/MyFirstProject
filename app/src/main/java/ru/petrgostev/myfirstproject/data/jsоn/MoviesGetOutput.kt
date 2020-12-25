package ru.petrgostev.myfirstproject.data.jsоson

import ru.petrgostev.myfirstproject.data.Movie

interface MoviesGetOutput {
    suspend fun getMovies(): List<Movie>

}