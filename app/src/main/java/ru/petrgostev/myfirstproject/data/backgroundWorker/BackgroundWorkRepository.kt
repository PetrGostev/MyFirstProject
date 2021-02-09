package ru.petrgostev.myfirstproject.data.backgroundWorker

import androidx.work.*
import java.util.concurrent.TimeUnit

class BackgroundWorkRepository() {

    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    val configurationRequest = PeriodicWorkRequestBuilder<BackgroundWorker>(INTERVAL, TimeUnit.HOURS)
        .setConstraints(constraints)
        .setInitialDelay(INTERVAL, TimeUnit.HOURS)
        .build()

    companion object {
        private const val INTERVAL = 24L
    }
}