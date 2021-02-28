package ru.petrgostev.myfirstproject.ui.moviesList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.data.repository.IConfigurationRepository
import ru.petrgostev.myfirstproject.data.repository.IMoviesRepository
import ru.petrgostev.myfirstproject.utils.*

class MoviesListViewModel(
    private val configurationRepository: IConfigurationRepository,
    private val moviesRepository: IMoviesRepository
) : ViewModel() {

    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    private val _mutableMoviesPagingList = MutableLiveData<PagingData<MoviesEntity>>()

    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val moviesPagingList: LiveData<PagingData<MoviesEntity>> get() = _mutableMoviesPagingList

    private var mCategory: Category = Category.POPULAR
    private var moviesResult: LiveData<PagingData<MoviesEntity>>? = null

    private val pagingObserver = Observer<PagingData<MoviesEntity>> {
        _mutableMoviesPagingList.postValue(it)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    fun getData(category: Category, isRefresh: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            loadConfiguration(category, isRefresh)
        }
    }

    private suspend fun loadConfiguration(category: Category, isRefresh: Boolean) {
        viewModelScope.launch {
            configurationRepository.checkUpdateDate()
            loadMovies(category, isRefresh)
        }
    }

    private fun loadMovies(category: Category, isRefresh: Boolean) {
        if (moviesResult != null && this.mCategory == category && !isRefresh) {
            return
        }

        this.mCategory = category

        moviesResult = moviesRepository.getMovies(category).cachedIn(viewModelScope)
        moviesResult?.observeForever(pagingObserver)
    }

    override fun onCleared() {
        moviesResult?.removeObserver(pagingObserver)
        super.onCleared()
    }
}