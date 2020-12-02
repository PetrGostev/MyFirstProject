package ru.petrgostev.myfirstproject.moviesDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.moviesDetails.adapter.ActorViewsAdapter
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesDetailsBinding

class MoviesDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    companion object {
        private const val MOVIE = "MOVIE"

        fun newInstance(movie: Movie): MoviesDetailsFragment {
            val fragment = MoviesDetailsFragment()
            val args = Bundle()
            args.putParcelable(MOVIE, movie)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var adapter: ActorViewsAdapter
    private var viewBinding: FragmentMoviesDetailsBinding? = null
    private var movie: Movie? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentMoviesDetailsBinding.bind(view)
        movie = arguments?.getParcelable(MOVIE)

        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun setupViews() {
        viewBinding!!.ageLimit.text = requireActivity().getString(R.string.age_limit, movie?.ageLimit)
        viewBinding!!.name.text = movie?.name?:""
        viewBinding!!.category.text = movie?.category
        viewBinding!!.rating.rating = movie?.rating!!
        viewBinding!!.reviewsQuantity.text = requireActivity().getString(R.string.reviews_quantity, movie?.reviewsQuantity)

        adapter = ActorViewsAdapter(requireContext())
        viewBinding!!.actorsRecycler.adapter = adapter
        adapter.submitList(movie?.actors)

        viewBinding!!.backButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }
}

