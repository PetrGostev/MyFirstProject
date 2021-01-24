package ru.petrgostev.myfirstproject.moviesDetails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesDetailsBinding
import ru.petrgostev.myfirstproject.di.App
import ru.petrgostev.myfirstproject.data.repository.NetworkRepository
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.repository.NetworkRepositoryInterface

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private val networkRepository: NetworkRepositoryInterface by lazy {
        NetworkRepository(
            App.component.getNetworkModule(),
        )
    }

    private val viewModel: MoviesDetailsViewModel by viewModels {
        MoviesDetailsViewModelFactory(networkRepository)
    }

    private var viewBinding: FragmentMoviesDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesDetailsBinding.bind(view)

        viewModel.movie.observe(this.viewLifecycleOwner, this::setupMovie)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovie(requireArguments().getInt(ARG_MOVIE_ID))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun setupMovie(movieDetails: MovieDetailsResponse) {
        val genresList: List<String> = movieDetails.genres.map { it.name }

        with(viewBinding ?: return) {

            image.load(movieDetails.moviePoster) {
                crossfade(true)
                error(R.drawable.poster_none)
            }

            minimumAge.text = getString(R.string.age_limit, movieDetails.minimumAge)
            title.text = movieDetails.title
            this.genres.text = genresList.joinToString()
            rating.rating = movieDetails.rating_5
            reviewsQuantity.text = getString(R.string.reviews_quantity, movieDetails.voteCount)
            storylineTitle.isGone = movieDetails.overview.isEmpty()
            storylineText.text = movieDetails.overview

            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    companion object {
        private const val ARG_MOVIE_ID = "movieId"

        fun newInstance(movieId: Int): MoviesDetailsFragment = MoviesDetailsFragment().apply {
            arguments = bundleOf(
                ARG_MOVIE_ID to movieId
            )
        }
    }
}
