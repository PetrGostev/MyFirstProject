package ru.petrgostev.myfirstproject.moviesList

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.Router
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding
import ru.petrgostev.myfirstproject.moviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.moviesList.padding.adapter.MovieLoadStateAdapter
import ru.petrgostev.myfirstproject.network.NetworkRepository
import ru.petrgostev.myfirstproject.network.pojo.MoviesItem
import ru.petrgostev.myfirstproject.utils.*

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val parentRouter: Router? by lazy { activity as? Router }

    private var moviesJob: Job? = null

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(NetworkRepository())
    }

    private var viewBinding: FragmentMoviesListBinding? = null
    private var sort: Category = Category.POPULAR
    private var isRestart = false

    private val adapter: MovieViewsAdapter by lazy {
        MovieViewsAdapter { movie: MoviesItem ->
            movie.id?.let { parentRouter?.openMoviesDetailsFragment(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isConnected.observe(this.viewLifecycleOwner, this::showToastNoConnectionYet)

        initViews(view)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getConfiguration()
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

        configureMoviesSwipe()
        configureSpinner()

        with(viewBinding ?: return) {
            initAdapter()
            retryButton.setOnClickListener { adapter.retry() }
        }
    }

    private fun initAdapter() {
        viewBinding?.moviesRecycler?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            with(viewBinding ?: return@addLoadStateListener) {
                moviesRecycler.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                loader.isVisible = loadState.source.refresh is LoadState.Loading

                if (loadState.source.refresh is LoadState.Loading) {
                    moviesSwipe.isRefreshing =  false
                }
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun configureMoviesSwipe() {
        with(viewBinding ?: return) {

            moviesSwipe.setDistanceToTriggerSync(Companion.DISTANCE_TRIGGER)

            moviesSwipe.setOnRefreshListener {
                if (Connect.isConnected) {
                    updateMovies(isRefresh = true)
                    isRestart = false
                } else {
                    moviesSwipe.isRefreshing = false
                    ToastUtil.showToastNotConnected(requireContext())
                }
            }
        }
    }

    private fun updateMovies(isRefresh: Boolean) {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            viewModel.loadMovies(sort, isRefresh).collectLatest {
                adapter.submitData(it)
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

                    if (!Connect.isConnected) {
                        moviesSwipe.isRefreshing = false
                        ToastUtil.showToastNotConnected(requireContext())
                        return
                    }

                    if (!isRestart) {
                        viewBinding?.loader?.isVisible = true
                        updateMovies(isRefresh = false)
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

    private fun showToastNoConnectionYet(isConnect: Boolean) {
        if (!isConnect) {
            viewBinding?.moviesSwipe?.isRefreshing = false
            ToastUtil.showToastNoConnectionYet(requireContext())
        }
    }

    companion object {
        private const val DISTANCE_TRIGGER = 10
    }
}