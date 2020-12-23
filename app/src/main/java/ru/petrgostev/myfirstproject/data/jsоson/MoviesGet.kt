package ru.petrgostev.myfirstproject.data.jsоson

import android.content.Context
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.data.loadMovies

class MoviesGet(private val context: Context) : MoviesGetOutput {

    override suspend fun getMovies(): List<Movie> {
        return loadMovies(context)
    }
}