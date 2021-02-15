package ru.petrgostev.myfirstproject.data.notifications

import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity

interface IMovieNotifications {
    fun initialize()
    fun showNotification(moviesEntity: MoviesEntity)
    fun dismissNotification(movieId: Long)
}