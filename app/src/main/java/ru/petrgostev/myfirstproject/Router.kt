package ru.petrgostev.myfirstproject

import android.view.View

interface Router {
    fun openMoviesDetailsFragment(view: View?, movieId: Int)
}