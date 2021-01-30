package ru.petrgostev.myfirstproject.ui.moviesList

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import ru.petrgostev.myfirstproject.data.dataBase.entity.*
import ru.petrgostev.myfirstproject.data.network.pojo.GenresItem
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacadeInterface
import ru.petrgostev.myfirstproject.utils.*
import java.util.*

class MoviesListViewModel(private val repositoriesFacade: RepositoriesFacadeInterface) : ViewModel() {

    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    private val _mutableMoviesPagingList = MutableLiveData<PagingData<MoviesViewItem>>()

    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val moviesPagingList: LiveData<PagingData<MoviesViewItem>> get() = _mutableMoviesPagingList

    private var sort: Category = Category.POPULAR
    private var moviesResult: LiveData<PagingData<MoviesViewItem>>? = null

    private val observer = Observer<PagingData<MoviesViewItem>> {
        _mutableMoviesPagingList.postValue(it)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception, scope active:${viewModelScope.isActive}", throwable)
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    fun getData(sort: Category, isRefresh: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            loadConfiguration(sort, isRefresh)
        }
    }

    private suspend fun loadConfiguration(sort: Category, isRefresh: Boolean) {
        viewModelScope.launch {
            checkUpdateDate()
            loadImagesAndGenres()
            loadMovies(sort, isRefresh)
        }
    }

    private suspend fun checkUpdateDate() {
        val updateDate = repositoriesFacade.getDateUpdateEntity()?.dateUpdate
        if (updateDate != null) {
            MoviesDate.IS_RELEVANT_UPDATE_DATE =
                MoviesDate.FORMAT_DATE.format(updateDate) == MoviesDate.FORMAT_DATE.format(Date())
        }
    }

    private suspend fun loadImagesAndGenres() {
        val imagesEntity = repositoriesFacade.getImagesEntity()
        val genres: List<GenresEntity>? = repositoriesFacade.getGenresEntities()

        if (imagesEntity != null && genres != null) {

            if (ImagesBaseUrl.IMAGES_BASE_URL.isEmpty()) {
                ImagesBaseUrl.IMAGES_BASE_URL = imagesEntity.secureBaseUrl ?: ""
            }
            if (PosterSizeList.posterSizes.isEmpty()) {
                PosterSizeList.posterSizes = imagesEntity.posterSizes
            }

            for (genre in genres) {
                GenresMap.genres[genre.id.toInt()] = genre.name
            }
            return
        }

        val images = repositoriesFacade.getImages()
        val genresResponse: List<GenresItem> = repositoriesFacade.getGenres()

        ImagesBaseUrl.IMAGES_BASE_URL = images.secureBaseUrl
        PosterSizeList.posterSizes = images.posterSizes

        repositoriesFacade.setImagesEntity(
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
        repositoriesFacade.setGenresEntities(genresEntities)
        repositoriesFacade.setDateUpdateEntity(DateUpdateEntity(dateUpdate = Date()))
    }

    private fun loadMovies(sort: Category, isRefresh: Boolean) {
        if (moviesResult != null && this.sort == sort && !isRefresh) {
            return
        }

        this.sort = sort

        moviesResult = repositoriesFacade.getMovies(sort).cachedIn(viewModelScope)
        moviesResult?.observeForever(observer)
    }

    override fun onCleared() {
        moviesResult?.removeObserver(observer)
        super.onCleared()
    }
}