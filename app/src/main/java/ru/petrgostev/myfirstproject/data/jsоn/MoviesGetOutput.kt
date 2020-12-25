package ru.petrgostev.myfirstproject.data.jsоn

import ru.petrgostev.myfirstproject.data.Movie

interface MoviesGetOutput {
    suspend operator fun invoke(): List<Movie>
}