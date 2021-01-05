package ru.petrgostev.myfirstproject.moviesDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.petrgostev.myfirstproject.network.NetworkRepository

class MoviesDetailsViewModelFactory (private val networkRepository: NetworkRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(networkRepository)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}