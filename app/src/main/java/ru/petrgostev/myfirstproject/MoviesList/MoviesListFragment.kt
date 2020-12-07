package ru.petrgostev.myfirstproject.MoviesList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.petrgostev.myfirstproject.MainActivityInterface
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.MoviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding
import ru.petrgostev.myfirstproject.mocks.Mock

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var mainActivityInterface: MainActivityInterface? = null
    private var viewBinding: FragmentMoviesListBinding? = null
    private lateinit var adapter: MovieViewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMoviesListBinding.bind(view)

        adapter = MovieViewsAdapter { movie: Movie -> mainActivityInterface?.onShowMoviesDetailsFragment(movie) }
        viewBinding?.moviesRecycler?.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityInterface = context as? MainActivityInterface
    }

    override fun onStart() {
        super.onStart()
        adapter.submitList(Mock().getMovies())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}