package ru.petrgostev.myfirstproject.data.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import ru.petrgostev.myfirstproject.MainActivity
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity

class MovieNotifications(private val context: Context) : IMovieNotifications {

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun initialize() {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(
                    CHANNEL_NEW_MOVIES,
                    NotificationManagerCompat.IMPORTANCE_HIGH
                ).setName(context.getString(R.string.channel_new_movies))
                    .setDescription(context.getString(R.string.channel_new_movies_description))
                    .build()
            )
        }
    }

    override fun showNotification(moviesEntity: MoviesEntity) {
        val contentUri = (URI + moviesEntity.id).toUri()

        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setContentTitle(context.getString(R.string.film_high_rating))
            .setSmallIcon(R.drawable.vector_like)
            .setContentText(moviesEntity.title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        notificationManagerCompat.notify(MOVIE_TAG, moviesEntity.id.toInt(), builder.build())
    }

    override fun dismissNotification(movieId: Long) {
        notificationManagerCompat.cancel(MOVIE_TAG, movieId.toInt())
    }

    companion object {
        private const val URI =
            "https://android.ru.petrgostev.myfirstproject.com/MoviesDetailsFragment/"
        private const val CHANNEL_NEW_MOVIES = "new_movies"
        private const val REQUEST_CONTENT = 1
        private const val MOVIE_TAG = "movie"
    }
}