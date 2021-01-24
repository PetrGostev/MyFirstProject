package ru.petrgostev.myfirstproject.moviesList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacade
import ru.petrgostev.myfirstproject.data.dataBase.entity.DateUpdateEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.*
import java.util.*

class MoviesListViewModel(private val repositoriesFacade: RepositoriesFacade) : ViewModel() {

    private val networkRepository = repositoriesFacade.networkRepository
    private val dataBaseRepository = repositoriesFacade.dataBaseRepository

    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    private val _mutableMoviesPagingList = MutableLiveData<PagingData<MoviesItem>>()

    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val moviesPagingList: LiveData<PagingData<MoviesItem>> get() = _mutableMoviesPagingList

    private var sort: Category = Category.POPULAR
    private var moviesResult: LiveData<PagingData<MoviesItem>>? = null
    private var isRelevantUpdateDate = true

    private val observer = Observer<PagingData<MoviesItem>> {
        _mutableMoviesPagingList.postValue(it)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    fun getConfiguration(sort: Category, isRefresh: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            loadConfiguration(sort, isRefresh)
        }
    }

    private suspend fun loadConfiguration(sort: Category, isRefresh: Boolean) {
        viewModelScope.launch {
            checkUpdateDate()
            loadImages()
            loadMovies(sort, isRefresh)
        }
    }

    private suspend fun checkUpdateDate() {
        val updateDate = dataBaseRepository.getDateUpdateEntity()?.dateUpdate
        if (updateDate == null) {
            isRelevantUpdateDate = false
        } else {
            isRelevantUpdateDate =
                FormatDate.FORMAT_DATE.format(updateDate) == FormatDate.FORMAT_DATE.format(Date())
        }
    }

    private suspend fun loadImages() {
        if (isRelevantUpdateDate) {
            val imagesEntity = dataBaseRepository.getImages()

            if (ImagesBaseUrl.IMAGES_BASE_URL.isEmpty()) {
                ImagesBaseUrl.IMAGES_BASE_URL = imagesEntity?.secureBaseUrl ?: ""
            }
            if (PosterSizeList.posterSizes.isEmpty() && imagesEntity != null) {
                PosterSizeList.posterSizes = imagesEntity.posterSizes
            }

            val genres: List<GenresEntity>? = dataBaseRepository.getGenres()
            if (genres != null && GenresMap.genres.isEmpty()) {
                for (genre in genres) {
                    GenresMap.genres[genre.id.toInt()] = genre.name
                }
            }
            return
        }

        val images = networkRepository.getImages()
        val genresResponse: List<GenresItem> = networkRepository.getGenres()

        ImagesBaseUrl.IMAGES_BASE_URL = images.secureBaseUrl
        PosterSizeList.posterSizes = images.posterSizes

        dataBaseRepository.setImages(
            ImagesEntity(
                posterSizes = images.posterSizes,
                secureBaseUrl = images.secureBaseUrl
            )
        )

        val genresEntities: MutableList<GenresEntity> = mutableListOf()

        for (genre in genresResponse) {
            GenresMap.genres[genre.id] = genre.name
            genresEntities.add(GenresEntity(id = genre.id.toLong(), name = genre.name))
        }
        dataBaseRepository.setGenres(genresEntities)
        dataBaseRepository.setDateUpdateEntity(DateUpdateEntity(dateUpdate = Date()))
    }

    private fun loadMovies(sort: Category, isRefresh: Boolean) {
        if (moviesResult != null && this.sort == sort && !isRefresh) {
            return
        }

        this.sort = sort

        viewModelScope.launch {
            moviesResult = networkRepository.getMovies(sort).cachedIn(viewModelScope)
            moviesResult?.observeForever(observer)

            _mutableIsConnected.postValue(true)
        }
    }

    override fun onCleared() {
        moviesResult?.removeObserver(observer)
        super.onCleared()
    }
}