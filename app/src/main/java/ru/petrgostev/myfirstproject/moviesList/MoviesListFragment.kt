package ru.petrgostev.myfirstproject.moviesList

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.Router
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding
import ru.petrgostev.myfirstproject.moviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.network.NetworkRepository
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.*

private const val DISTANCE_TRIGGER = 10

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val parentRouter: Router? by lazy { activity as? Router }

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(NetworkRepository())
    }

    private var viewBinding: FragmentMoviesListBinding? = null
    private var page = 1
    private var sort: Category = Category.POPULAR
    private var isRestart = false

    private val adapter: MovieViewsAdapter by lazy {
        MovieViewsAdapter { movie: MoviesItem ->
            parentRouter?.openMoviesDetailsFragment(movie.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.moviesList.observe(
            this.viewLifecycleOwner,
            { if (it.isNotEmpty()) this.updateAdapter(it) })
        viewModel.isConnected.observe(this.viewLifecycleOwner, this::showToastNoConnectionYet)
        viewModel.isLoading.observe(this.viewLifecycleOwner, this::showLoader)

        initViews(view)
    }

    override fun onPause() {
        super.onPause()
        isRestart = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initViews(view: View) {
        viewBinding = FragmentMoviesListBinding.bind(view)

        configureMoviesSwipy()
        configureSpinner()

        viewBinding?.moviesRecycler?.adapter = adapter
    }

    private fun updateAdapter(movieResponses: List<MoviesItem>) {
        adapter.submitList(movieResponses)
    }

    private fun configureMoviesSwipy() {
        with(viewBinding ?: return) {

            moviesSwipy.direction = SwipyRefreshLayoutDirection.BOTH
            moviesSwipy.setDistanceToTriggerSync(DISTANCE_TRIGGER)

            moviesSwipy.setOnRefreshListener {
                when (it) {
                    SwipyRefreshLayoutDirection.TOP -> page = 1
                    SwipyRefreshLayoutDirection.BOTTOM -> page++
                    else -> page = 1
                }
                if (Connect.isConnected) {
                    viewModel.getMovies(page, sort)
                    isRestart = false
                } else {
                    moviesSwipy.isRefreshing = false
                    ToastUtil.showToastNotConnected(requireContext())
                }
            }
        }
    }

    private fun configureSpinner() {
        with(viewBinding ?: return) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> sort = Category.POPULAR
                        1 -> sort = Category.TOP_RATED
                        2 -> sort = Category.UPCOMING
                    }

                    setSelectionWithoutDispatch(spinner, position)

                    page = 1

                    if (!Connect.isConnected) {
                        moviesSwipy.isRefreshing = false
                        ToastUtil.showToastNotConnected(requireContext())
                        return
                    }

                    if (!isRestart) {
                        viewModel.getMovies(page, sort)
                    }
                    isRestart = false
                }
            }
        }
    }

    private fun setSelectionWithoutDispatch(spinner: Spinner, position: Int) {
        val onItemSelectedListener = spinner.onItemSelectedListener
        spinner.onItemSelectedListener = null
        spinner.setSelection(position, false)
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun showLoader(isLoading: Boolean) {
        with(viewBinding ?: return) {
            moviesSwipy.isRefreshing = false
            loader.isVisible = isLoading
        }
    }

    private fun showToastNoConnectionYet(isConnect: Boolean) {
        if (!isConnect) {
            viewBinding?.moviesSwipy?.isRefreshing = false
            ToastUtil.showToastNoConnectionYet(requireContext())
        }
    }
}