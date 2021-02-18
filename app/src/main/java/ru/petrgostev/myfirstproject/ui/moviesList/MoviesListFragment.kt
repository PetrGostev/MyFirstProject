package ru.petrgostev.myfirstproject.ui.moviesList

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.transition.TransitionInflater
import androidx.work.WorkManager
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.petrgostev.myfirstproject.MainActivity
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.Router
import ru.petrgostev.myfirstproject.data.dataBase.MoviesDataBase
import ru.petrgostev.myfirstproject.data.repository.ConfigurationRepository
import ru.petrgostev.myfirstproject.data.repository.IConfigurationRepository
import ru.petrgostev.myfirstproject.data.repository.IMoviesRepository
import ru.petrgostev.myfirstproject.data.repository.MoviesRepository
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding
import ru.petrgostev.myfirstproject.ui.moviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.ui.moviesList.padding.adapter.MovieLoadStateAdapter
import ru.petrgostev.myfirstproject.data.backgroundWorker.BackgroundWorkRepository
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.di.App
import ru.petrgostev.myfirstproject.ui.moviesDetails.MoviesDetailsFragment
import ru.petrgostev.myfirstproject.utils.*

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val parentRouter: Router? by lazy { activity as? Router }
    private val networkModule = App.component.getNetworkModule()
    private val moviesDataBase = MoviesDataBase.INSTANCE

    private val configurationRepository: IConfigurationRepository by lazy {
        ConfigurationRepository(
            networkModule, moviesDataBase.dateUpdateDao(),
            moviesDataBase.imagesDao(),
            moviesDataBase.genresDao()
        )
    }
    private val moviesRepository: IMoviesRepository by lazy {
        MoviesRepository.getInstance(
            requireContext(),
            networkModule,
            moviesDataBase.moviesDao()
        )
    }

    private var moviesJob: Job? = null

    private val workRepository = BackgroundWorkRepository()

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(configurationRepository, moviesRepository)
    }

    private var viewBinding: FragmentMoviesListBinding? = null
    private var category: Category = Category.POPULAR
    private var positionAdapterView: View? = null

    private val adapter: MovieViewsAdapter by lazy {
        MovieViewsAdapter(fun(view: View, movie: MoviesEntity) {
            positionAdapterView = view
            movie.id.let { openMoviesDetailsFragment(it.toInt()) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        viewModel.isConnected.observe(this.viewLifecycleOwner, this::showToastNoConnectionYet)
        viewModel.moviesPagingList.observe(this.viewLifecycleOwner, {
            this.updateAdapter(it)
        })

        exitTransition = MaterialElevationScale(false).apply { duration = 500 }
//        reenterTransition = MaterialElevationScale(true).apply { duration = 500 }

//        postponeEnterTransition()
//        view.doOnPreDraw { startPostponedEnterTransition() }


        WorkManager.getInstance(requireContext()).enqueue(workRepository.configurationRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun initViews(view: View) {
        viewBinding = FragmentMoviesListBinding.bind(view)

        configureMoviesSwipe()
        configureSpinner()
        initAdapter()

        viewBinding?.retryButton?.setOnClickListener { adapter.retry() }
    }

    private fun initAdapter() {
        viewBinding?.moviesRecycler?.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            viewBinding?.moviesRecycler?.isVisible = loadState.source.refresh is LoadState.NotLoading
            viewBinding?.loader?.isVisible = loadState.source.refresh is LoadState.Loading
            viewBinding?.moviesSwipe?.isRefreshing = false
            viewBinding?.retryButton?.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_state, it.error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateAdapter(movies: PagingData<MoviesEntity>) {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            adapter.submitData(movies)
        }
    }

    private fun configureMoviesSwipe() {
        with(viewBinding ?: return) {

            moviesSwipe.setDistanceToTriggerSync(Companion.DISTANCE_TRIGGER)

            moviesSwipe.setOnRefreshListener {
                if (Connect.isConnected) {
                    viewModel.getData(category = category, isRefresh = true)
                } else {
                    moviesSwipe.isRefreshing = false
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
                        POSITION_POPULAR -> category = Category.POPULAR
                        POSITION_TOP_RATED -> category = Category.TOP_RATED
                        POSITION_UPCOMING -> category = Category.UPCOMING
                    }

                    if (!Connect.isConnected) {
                        moviesSwipe.isRefreshing = false
                        ToastUtil.showToastNotConnected(requireContext())
                        return
                    }

                    viewModel.getData(category = category, isRefresh = false)
                }
            }
        }
    }

    private fun showToastNoConnectionYet(isConnect: Boolean) {
        if (!isConnect) {
            viewBinding?.moviesSwipe?.isRefreshing = false
            ToastUtil.showToastNoConnectionYet(requireContext())
        }
    }

    private fun openMoviesDetailsFragment(movieId: Int) {
        if (!Connect.isConnected) {
            ToastUtil.showToastNotConnected(requireContext())
            return
        }

        val moviesDetailsFragment = MoviesDetailsFragment.newInstance(movieId)
        moviesDetailsFragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
//            drawingViewId = R.id.nav_host_fragment_container
            duration = 2000
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.BLACK)
        }
        moviesDetailsFragment.sharedElementReturnTransition = MaterialContainerTransform().apply {
                duration = 500
                scrimColor = Color.TRANSPARENT
                setAllContainerColors(Color.BLACK)
            }

        positionAdapterView?.let {
            val view: ViewGroup = it as ViewGroup
            val name = requireActivity().getString(R.string.shared_movie)

            requireActivity().supportFragmentManager.commit {
                addSharedElement(view, name)
                replace(R.id.fame, moviesDetailsFragment, FRAGMENT_MOVIE)
                addToBackStack(FRAGMENT_MOVIE)
            }

        }
    }

    companion object {
        private const val DISTANCE_TRIGGER = 10
        private const val POSITION_POPULAR = 0
        private const val POSITION_TOP_RATED = 1
        private const val POSITION_UPCOMING = 2
        private const val FRAGMENT_MOVIE = "movie"
        private const val MOVIE_ID = "movieId"
    }
}