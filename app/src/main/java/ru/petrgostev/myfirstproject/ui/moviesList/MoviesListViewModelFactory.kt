package ru.petrgostev.myfirstproject.ui.moviesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacadeInterface

class MoviesListViewModelFactory(private val repositoriesFacade: RepositoriesFacadeInterface) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(repositoriesFacade)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}