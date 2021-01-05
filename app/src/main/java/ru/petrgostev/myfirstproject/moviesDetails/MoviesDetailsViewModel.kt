package ru.petrgostev.myfirstproject.moviesDetails

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.network.NetworkRepository
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse

class MoviesDetailsViewModel(private val networkRepository: NetworkRepository) : ViewModel() {


    private val _mutableMovie = MutableLiveData<MovieDetailsResponse>()
    val movie: LiveData<MovieDetailsResponse> get() = _mutableMovie

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(ContentValues.TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
        }
    }

    fun loadMovie(movieId: Int) {
        viewModelScope.launch(exceptionHandler) {
            val moviesResponse = networkRepository.getMovie(movieId)
            _mutableMovie.postValue(moviesResponse)
        }
    }
}