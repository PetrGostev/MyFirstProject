package ru.petrgostev.myfirstproject.moviesList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import ru.petrgostev.myfirstproject.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.network.repository.NetworkRepositoryInterface
import ru.petrgostev.myfirstproject.utils.*

class MoviesListViewModel(private val networkRepository: NetworkRepositoryInterface) : ViewModel() {

    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    private val _mutableMoviesPagingList = MutableLiveData<PagingData<MoviesItem>>()

    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val moviesPagingList: LiveData<PagingData<MoviesItem>> get() = _mutableMoviesPagingList

    private val genres = GenresMap.genres
    private var sort: Category = Category.POPULAR
    private var moviesResult: LiveData<PagingData<MoviesItem>>? = null

    private val observer =  Observer<PagingData<MoviesItem>>{
        _mutableMoviesPagingList.postValue(it)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    fun getConfiguration() {
        viewModelScope.launch(exceptionHandler) {
            loadConfigurationAndGenresAndMovies()
        }
    }

    private suspend fun loadConfigurationAndGenresAndMovies() {
        loadConfiguration()
        loadGenres()
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

    fun getMovies(sort: Category, isRefresh: Boolean) {
        if (moviesResult != null && this.sort == sort && !isRefresh) {
            return
        }

        this.sort = sort

        viewModelScope.launch {
            moviesResult = networkRepository.getMovies(sort).cachedIn(viewModelScope)
            moviesResult?.observeForever (observer)

            _mutableIsConnected.postValue(true)
        }
    }

    override fun onCleared() {
        moviesResult?.removeObserver(observer)
        super.onCleared()
    }
}