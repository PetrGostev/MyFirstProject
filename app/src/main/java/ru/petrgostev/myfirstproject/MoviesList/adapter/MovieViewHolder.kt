package ru.petrgostev.myfirstproject.MoviesList.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.databinding.ViewHolderMovieBinding

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        private val imageOption = RequestOptions()
            .placeholder(R.drawable.poster_none)
            .fallback(R.drawable.poster_none)
    }

    private val viewBinding = ViewHolderMovieBinding.bind(itemView)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
            .load(movie.imagePoster)
            .apply(imageOption)
            .into(viewBinding.movieImagePoster)

        viewBinding.movieAgeLimit.text =
            itemView.context.getString(R.string.age_limit, movie.ageLimit)

        val resId: Int = if (movie.like) {
            R.drawable.vector_like
        } else {
            R.drawable.vector_no_like
        }

        with(viewBinding) {
            movieAgeLimit.text = itemView.context.getString(R.string.age_limit, movie.ageLimit)
            movieLike.setImageResource(resId)
            movieCategory.text = movie.category
            movieRating.rating = movie.rating
            movieReviewsQuantity.text = movie.reviewsQuantity.toString()
            movieReviewsQuantity.text = itemView.context.getString(R.string.reviews_quantity, movie.reviewsQuantity)
            movieName.text = movie.name
            movieDuration.text = itemView.context.getString(R.string.duration_text, movie.duration)
        }
    }
}