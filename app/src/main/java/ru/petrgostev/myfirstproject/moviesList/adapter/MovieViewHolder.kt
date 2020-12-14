package ru.petrgostev.myfirstproject.moviesList.adapter

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
            .fallback(R.drawable.poster_none)
    }

    private val viewBinding = ViewHolderMovieBinding.bind(itemView)

    fun onBind(movie: Movie) {
        Glide.with(itemView.context)
            .load(movie.poster)
            .apply(imageOption)
            .into(viewBinding.movieImagePoster)

        val resId: Int = if (movie.numberOfRatings > 1000) {
            R.drawable.vector_like
        } else {
            R.drawable.vector_no_like
        }

        val genres:List<String> = movie.genres.map { it.name }

        with(viewBinding) {
            movieAgeLimit.text = itemView.context.getString(R.string.age_limit, movie.minimumAge)
            movieLike.setImageResource(resId)
            movieGenres.text = genres.joinToString()
            movieRating.rating = movie.rating_5
            movieReviewsQuantity.text =
                itemView.context.getString(R.string.reviews_quantity, movie.numberOfRatings)
            movieTitle.text = movie.title
            movieRuntime.text = itemView.context.getString(R.string.runtime_text, movie.runtime)
        }
    }
}