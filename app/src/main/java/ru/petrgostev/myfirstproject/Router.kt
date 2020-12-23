package ru.petrgostev.myfirstproject

import ru.petrgostev.myfirstproject.data.Movie

interface Router {
    fun openMoviesDetailsFragment(movie: Movie)
}