package ru.petrgostev.myfirstproject.ui.moviesDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.repository.IMoviesRepository

class MoviesDetailsViewModel(private val moviesRepository: IMoviesRepository) : ViewModel() {

    val _mutableMovie = MutableLiveData<MovieDetailsResponse>()
    val movie: LiveData<MovieDetailsResponse> get() = _mutableMovie

    fun loadMovie(movieId: Int) {
        viewModelScope.launch() {
            val moviesResponse =
                moviesRepository.getMovie(movieId)
            _mutableMovie.postValue(moviesResponse)
        }
    }
}