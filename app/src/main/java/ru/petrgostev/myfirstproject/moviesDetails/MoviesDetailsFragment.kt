package ru.petrgostev.myfirstproject.moviesDetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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

        with(viewBinding) {
            this?.ageLimit?.text = requireActivity().getString(R.string.age_limit, movie?.ageLimit)
            this?.name?.text = movie?.name.orEmpty()
            this?.category?.text = movie?.category
            this?.rating?.rating = movie?.rating?: 0.0f
            this?.reviewsQuantity?.text = requireActivity().getString(R.string.reviews_quantity, movie?.reviewsQuantity)
            this?.actorsRecycler?.adapter = adapter
            adapter.submitList(movie?.actors)
            this?.backButton?.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}

