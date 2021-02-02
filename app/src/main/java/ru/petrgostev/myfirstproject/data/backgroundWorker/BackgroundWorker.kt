package ru.petrgostev.myfirstproject.data.backgroundWorker

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.petrgostev.myfirstproject.data.dataBase.MoviesDataBase
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.repository.ConfigurationRepository
import ru.petrgostev.myfirstproject.data.repository.IConfigurationRepository
import ru.petrgostev.myfirstproject.di.App

class BackgroundWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val networkModule = App.component.getNetworkModule()
    private val moviesDataBase = MoviesDataBase.INSTANCE

    private val configurationRepository: IConfigurationRepository by lazy {
        ConfigurationRepository(
            networkModule, moviesDataBase.dateUpdateDao(),
            moviesDataBase.imagesDao(),
            moviesDataBase.genresDao()
        )
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                updateImages()
                updateGenres()

                Result.success()
            } catch (e: Exception) {
                Log.d("MyWorker", "Exception getting location -->  ${e.message}")
                Result.failure()
            }
        }
    }

    private suspend fun updateImages() {
        val imagesResponse = configurationRepository.getImages()
        configurationRepository.setImagesEntity(
            ImagesEntity(
                posterSizes = imagesResponse.posterSizes,
                secureBaseUrl = imagesResponse.secureBaseUrl
            )
        )
    }

    private suspend fun updateGenres() {
        val genresResponse = configurationRepository.getGenres()
        val genresEntities = mutableListOf<GenresEntity>()
        for (genre in genresResponse) {
            genresEntities.add(GenresEntity(id = genre.id.toLong(), name = genre.name))
        }
        configurationRepository.setGenresEntities(genresEntities)
    }
}