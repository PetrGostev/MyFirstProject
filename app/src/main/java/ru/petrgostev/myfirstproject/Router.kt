package ru.petrgostev.myfirstproject

import ru.petrgostev.myfirstproject.data.Movie

interface MainActivityInterface {
    fun onShowMoviesDetailsFragment(movie: Movie)
}