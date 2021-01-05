package ru.petrgostev.myfirstproject.moviesList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import ru.petrgostev.myfirstproject.network.NetworkRepository
import ru.petrgostev.myfirstproject.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.GenresMap

class MoviesListViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _mutableMoviesList = MutableLiveData<List<MoviesItem>>(emptyList())
    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    private val _mutableIsLoading = MutableLiveData<Boolean>(true)

    val moviesList: LiveData<List<MoviesItem>> get() = _mutableMoviesList
    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val isLoading: LiveData<Boolean> get() = _mutableIsLoading

    private val genres = GenresMap.genres
    private var totalPages = 0

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    var movieResponses: ArrayList<MoviesItem> = arrayListOf()

    fun getMovies(page: Int, sort: Int) {
        viewModelScope.launch(exceptionHandler) {
            loadGenresAndMovies(page, sort)
        }
    }

    private suspend fun loadGenresAndMovies(page: Int, sort: Int) {
        loadGenres()
        loadMovies(page, sort)
    }

    private suspend fun loadGenres() {
        if (genres.isEmpty()) {
            val genresResponse: List<GenresItem> = networkRepository.getGenres()
            for (genre in genresResponse) {
                genres[genre.id] = genre.name
            }
        }
    }

    private suspend fun loadMovies(page: Int, sort: Int) {
        if (page == 1) {
            movieResponses = arrayListOf()
        }

        if (page < totalPages || totalPages == 0) {
            val moviesResponse = networkRepository.getMovies(page, sort)
            movieResponses.addAll(moviesResponse.movieResponses)
            _mutableMoviesList.postValue(movieResponses)
            _mutableIsConnected.postValue(true)
            _mutableIsLoading.postValue(false)

            this.totalPages = moviesResponse.totalPages
        }
    }
}