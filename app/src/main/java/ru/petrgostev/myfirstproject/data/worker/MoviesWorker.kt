package ru.petrgostev.myfirstproject.data.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacade

class MoviesWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val repositoriesFacade = RepositoriesFacade()

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
        val imagesResponse = repositoriesFacade.networkRepository.getImages()
        repositoriesFacade.dataBaseRepository.setImages(
            ImagesEntity(
                posterSizes = imagesResponse.posterSizes,
                secureBaseUrl = imagesResponse.secureBaseUrl
            )
        )
    }

    private suspend fun updateGenres() {
        val genresResponse = repositoriesFacade.dataBaseRepository.getGenres()
        val genresEntities = mutableListOf<GenresEntity>()
        if (genresResponse != null) {
            for (genre in genresResponse) {
                genresEntities.add(GenresEntity(id = genre.id, name = genre.name))
            }
        }
    }
}