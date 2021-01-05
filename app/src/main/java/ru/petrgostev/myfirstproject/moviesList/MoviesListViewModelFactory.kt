package ru.petrgostev.myfirstproject.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.petrgostev.myfirstproject.network.NetworkRepository

class MoviesListViewModelFactory(private val networkRepository: NetworkRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(networkRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}