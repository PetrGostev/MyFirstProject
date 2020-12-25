package ru.petrgostev.myfirstproject.data.jsоn

import android.content.Context
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.data.loadMovies

class MoviesGet(private val context: Context) : MoviesGetOutput {

    override suspend fun invoke(): List<Movie> = loadMovies(context)
}