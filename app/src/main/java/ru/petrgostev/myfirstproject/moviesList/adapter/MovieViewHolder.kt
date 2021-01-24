package ru.petrgostev.myfirstproject.moviesList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aids61517.easyratingview.EasyRatingView
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.network.pojo.MoviesItem

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val movieImagePoster: ImageView = itemView.findViewById(R.id.movie_image_poster)
    private val movieAgeLimit: TextView = itemView.findViewById(R.id.movie_age_limit)
    private val movieGenres: TextView = itemView.findViewById(R.id.movie_genres)
    private val movieRating: EasyRatingView = itemView.findViewById(R.id.movie_rating)
    private val movieReviewsQuantity: TextView = itemView.findViewById(R.id.movie_reviews_quantity)
    private val movieTitle: TextView = itemView.findViewById(R.id.movie_title)

    fun onBind(moviesItem: MoviesItem) {

        movieImagePoster.load(moviesItem.moviePoster) {
            crossfade(false)
            error(R.drawable.poster_none)
        }

        movieAgeLimit.text = itemView.context.getString(R.string.age_limit, moviesItem.minimumAge)
        //TODO 01: movieLike.setImageResource(resId)
        movieGenres.text = moviesItem.genre().toString()
        movieRating.rating = moviesItem.rating_5
        movieReviewsQuantity.text = itemView.context.getString(R.string.reviews_quantity, moviesItem.voteCount)
        movieTitle.text = moviesItem.title
    }
}