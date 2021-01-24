package ru.petrgostev.myfirstproject.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacade

class MoviesListViewModelFactory(private val repositoriesFacade: RepositoriesFacade) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(repositoriesFacade)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}