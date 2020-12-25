package ru.petrgostev.myfirstproject.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.data.jsоn.MoviesGetOutput

class MoviesListViewModel(private val moviesGetOutput: MoviesGetOutput) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<Movie>>(emptyList())

    val moviesList: LiveData<List<Movie>> get() = _mutableMoviesList

    fun loadMovies() {
        viewModelScope.launch {
            val moviesResult: List<Movie> = moviesGetOutput()
            _mutableMoviesList.setValue(moviesResult)
        }
    }
}