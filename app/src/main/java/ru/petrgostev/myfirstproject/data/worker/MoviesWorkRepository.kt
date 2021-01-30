package ru.petrgostev.myfirstproject.data.worker

import androidx.work.*
import ru.petrgostev.myfirstproject.data.repository.RepositoriesFacade
import java.util.concurrent.TimeUnit

class MoviesWorkRepository() {

    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    val configurationRequest = PeriodicWorkRequestBuilder<MoviesWorker>(INTERVAL, TimeUnit.HOURS)
        .setConstraints(constraints)
        .setInitialDelay(INTERVAL, TimeUnit.HOURS)
        .build()

    companion object {
        private const val INTERVAL = 24L
    }
}