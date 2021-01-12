package ru.petrgostev.myfirstproject.moviesDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.network.repository.NetworkRepositoryInterface

class MoviesDetailsViewModel(private val networkRepository: NetworkRepositoryInterface) : ViewModel() {

    private val _mutableMovie = MutableLiveData<MovieDetailsResponse>()
    val movie: LiveData<MovieDetailsResponse> get() = _mutableMovie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch() {
            val moviesResponse =
                networkRepository.getMovie(movieId)
            _mutableMovie.postValue(moviesResponse)
        }
    }
}