package ru.petrgostev.myfirstproject.moviesList.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.databinding.ViewHolderMovieBinding
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.ImagesBaseUrl
import ru.petrgostev.myfirstproject.utils.PosterSizeEnum

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewBinding = ViewHolderMovieBinding.bind(itemView)

    fun onBind(moviesItem: MoviesItem) {

        viewBinding.movieImagePoster.load(ImagesBaseUrl.IMAGES_BASE_URL + PosterSizeEnum.W500.size + moviesItem.posterPath) {
            crossfade(false)
            error(R.drawable.poster_none)
        }

        with(viewBinding) {
            movieAgeLimit.text =
                itemView.context.getString(R.string.age_limit, moviesItem.minimumAge)
            //TODO 01: movieLike.setImageResource(resId)
            movieGenres.text = moviesItem.genre.toString()
            movieRating.rating = moviesItem.rating_5
            movieReviewsQuantity.text =
                itemView.context.getString(R.string.reviews_quantity, moviesItem.voteCount)
            movieTitle.text = moviesItem.title
        }
    }
}