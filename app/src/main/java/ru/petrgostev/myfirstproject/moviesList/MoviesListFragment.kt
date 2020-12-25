package ru.petrgostev.myfirstproject.moviesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.petrgostev.myfirstproject.Router
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.moviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.data.jsоson.MoviesGet
import ru.petrgostev.myfirstproject.data.jsоson.MoviesGetOutput
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val parentRouter: Router? get() = (activity as? Router)
    private val moviesGetOutput: MoviesGetOutput get() = (MoviesGet(requireContext()))

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(
            moviesGetOutput
        )
    }

    private var viewBinding: FragmentMoviesListBinding? = null
    private lateinit var adapter: MovieViewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        viewModel.moviesList.observe(this.viewLifecycleOwner, this::updateAdapter)
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initViews(view: View) {
        viewBinding = FragmentMoviesListBinding.bind(view)
        adapter = MovieViewsAdapter { movie: Movie ->
            parentRouter?.openMoviesDetailsFragment(movie)
        }
        viewBinding?.moviesRecycler?.adapter = adapter
    }

    private fun updateAdapter(movies: List<Movie>) {
        adapter.submitList(movies)
    }
}