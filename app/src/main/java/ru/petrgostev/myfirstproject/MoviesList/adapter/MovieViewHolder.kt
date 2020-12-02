package ru.petrgostev.myfirstproject.MoviesList.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aids61517.easyratingview.EasyRatingView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.poster_none)
            .fallback(R.drawable.poster_none)
    }

    private val poster: ImageView = itemView.findViewById(R.id.movie_image_poster)
    private val ageLimit: TextView = itemView.findViewById(R.id.movie_age_limit)
    private val like: ImageView = itemView.findViewById(R.id.movie_like)
    private val category: TextView = itemView.findViewById(R.id.movie_category)
    private val rating: EasyRatingView = itemView.findViewById(R.id.movie_rating)
    private val reviewsQuantity: TextView = itemView.findViewById(R.id.movie_reviews_quantity)
    private val name: TextView = itemView.findViewById(R.id.movie_name)
    private val duration: TextView = itemView.findViewById(R.id.movie_duration)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
            .load(movie.imagePoster)
            .apply(imageOption)
            .into(poster)

        ageLimit.text = itemView.context.getString(R.string.age_limit, movie.ageLimit)

        val resId: Int
        if (movie.like) {
            resId = R.drawable.vector_like
        } else {
            resId = R.drawable.vector_no_like
        }

        like.setImageResource(resId)
        category.text = movie.category
        rating.rating = movie.rating
        reviewsQuantity.text = movie.reviewsQuantity.toString()
        reviewsQuantity.text = itemView.context.getString(R.string.reviews_quantity, movie.reviewsQuantity)
        name.text = movie.name
        duration.text = itemView.context.getString(R.string.duration_text, movie.duration)
    }
}