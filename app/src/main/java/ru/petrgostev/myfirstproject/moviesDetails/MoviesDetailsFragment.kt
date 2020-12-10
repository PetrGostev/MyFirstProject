package ru.petrgostev.myfirstproject.moviesDetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.petrgostev.myfirstproject.MoviesList.adapter.MovieViewHolder
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.moviesDetails.adapter.ActorViewsAdapter
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesDetailsBinding

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    companion object {
        private const val ARG_MOVIE = "movie"

        fun newInstance(movie: Movie): MoviesDetailsFragment {
            return MoviesDetailsFragment().apply {
                arguments = bundleOf(
                    ARG_MOVIE to movie
                )
            }
        }
    }

    private var viewBinding: FragmentMoviesDetailsBinding? = null
    private var movie: Movie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesDetailsBinding.bind(view)
        movie = arguments?.getParcelable(ARG_MOVIE)

        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun setupViews() {
        val adapter = ActorViewsAdapter()

        val genres = arrayListOf<String>()
        movie?.genres?.forEach { i ->
            genres.add(i.name)
        }

        with(viewBinding) {
            this?.minimumAge?.text = requireActivity().getString(R.string.age_limit, movie?.minimumAge)
            this?.title?.text = movie?.title.orEmpty()
            this?.genres?.text = genres.joinToString()
            this?.rating?.rating = movie?.ratings?.div(2) ?: 0.0f
            this?.reviewsQuantity?.text = requireActivity().getString(R.string.reviews_quantity, movie?.numberOfRatings)
            this?.storylineText?.text = movie?.overview

            if (movie?.actors != null && movie?.actors?.isEmpty() == false) {
                this?.actorTitle?.visibility = View.VISIBLE
            } else {
                this?.actorTitle?.visibility = View.GONE
            }

            this?.actorsRecycler?.adapter = adapter

            adapter.submitList(movie?.actors)

            this?.backButton?.setOnClickListener {
                requireActivity().onBackPressed()
            }

            Glide.with(requireContext())
                .load(movie?.backdrop)
                .apply(
                    RequestOptions()
                    .fallback(R.drawable.poster_none)
                )
                .into(this?.image)
        }
    }
}

