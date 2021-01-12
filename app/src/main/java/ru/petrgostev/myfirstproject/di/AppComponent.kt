package ru.petrgostev.myfirstproject.di

import dagger.Component
import ru.petrgostev.myfirstproject.network.NetworkModule
import ru.petrgostev.myfirstproject.network.api.ConfigurationApi
import ru.petrgostev.myfirstproject.network.api.GenreApi
import ru.petrgostev.myfirstproject.network.api.MoviesApi

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getNetworkModule(): NetworkModule
//    fun getGenreApi(): GenreApi
//    fun getMoviesApi(): MoviesApi
}