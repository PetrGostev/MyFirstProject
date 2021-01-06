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
import ru.petrgostev.myfirstproject.utils.Category
import ru.petrgostev.myfirstproject.utils.GenresMap
import ru.petrgostev.myfirstproject.utils.ImagesBaseUrl
import ru.petrgostev.myfirstproject.utils.PosterSizeList

class MoviesListViewModel(private val networkRepository: NetworkRepository) : ViewModel() {

    private var _mutableMoviesList = MutableLiveData<List<MoviesItem>>(emptyList())
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

    fun getMovies(page: Int, sort: Category) {
        viewModelScope.launch(exceptionHandler) {
            loadConfigurationAndGenresAndMovies(page, sort)
        }
    }

    private suspend fun loadConfigurationAndGenresAndMovies(page: Int, sort: Category) {
        loadConfiguration()
        loadGenres()
        loadMovies(page, sort)
    }

    private suspend fun loadConfiguration() {
        val images = networkRepository.getImages()
        ImagesBaseUrl.IMAGES_BASE_URL = images.secureBaseUrl
        PosterSizeList.posterSizes = images.posterSizes
    }

    private suspend fun loadGenres() {
        if (genres.isEmpty()) {
            val genresResponse: List<GenresItem> = networkRepository.getGenres()
            for (genre in genresResponse) {
                genres[genre.id] = genre.name
            }
        }
    }

    private suspend fun loadMovies(page: Int, sort: Category) {
        if (page == 1) {
            _mutableMoviesList.value = emptyList()
        }

        if (page < totalPages || totalPages == 0) {
            val moviesResponse = networkRepository.getMovies(page, sort)

            val updatedMoviesList =
                _mutableMoviesList.value?.plus(moviesResponse.movieResponses).orEmpty()
            _mutableMoviesList.postValue(updatedMoviesList)
            _mutableIsConnected.postValue(true)
            _mutableIsLoading.postValue(false)

            this.totalPages = moviesResponse.totalPages
        }
    }
}