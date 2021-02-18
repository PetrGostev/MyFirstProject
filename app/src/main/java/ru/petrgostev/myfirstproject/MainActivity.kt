package ru.petrgostev.myfirstproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.transition.*
import ru.petrgostev.myfirstproject.ui.moviesDetails.MoviesDetailsFragment
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesListFragment
import ru.petrgostev.myfirstproject.utils.Connect
import ru.petrgostev.myfirstproject.utils.ConnectionType
import ru.petrgostev.myfirstproject.utils.NetworkMonitorUtil
import ru.petrgostev.myfirstproject.utils.ToastUtil

class MainActivity : AppCompatActivity(), Router {

    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNetworkMonitor()

        if (savedInstanceState == null) {
            openMoviesListFragment()
            intent?.let(::openMoviesDetailsFragmentFromIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        openMoviesDetailsFragmentFromIntent(intent)
    }

    override fun openMoviesDetailsFragment(view: View, movieId: Int) {
        if (!Connect.isConnected) {
            ToastUtil.showToastNotConnected(this)
            return
        }

        val moviesDetailsFragment = MoviesDetailsFragment.newInstance(movieId).apply {
            sharedElementEnterTransition = DetailsTransition()
            sharedElementReturnTransition = Slide()
        }

        supportFragmentManager.commit {
            addSharedElement(view, getString(R.string.shared_movie))
            replace(R.id.fame, moviesDetailsFragment)
            addToBackStack(null)
        }
    }

    private fun openMoviesDetailsFragmentFromIntent(intent: Intent?) {
        intent?.let {
            val id = intent.data?.lastPathSegment?.toLongOrNull()
//            id?.let { openMoviesDetailsFragment(view, id.toInt()) }
        }
    }

    private fun openMoviesListFragment() {
        if (!Connect.isConnected) {
            showNetworkErrorDialog()
            return
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fame, MoviesListFragment())
            .commit()
    }

    private fun initNetworkMonitor() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Connect.isConnected = true
                            }
                            ConnectionType.Cellular -> {
                                Connect.isConnected = true
                            }
                            else -> Unit
                        }
                    }
                    false -> {
                        Connect.isConnected = false
                    }
                }
            }
        }
    }

    private fun showNetworkErrorDialog() = AlertDialog.Builder(this)
        .setTitle(getString(R.string.no_connecting))
        .setPositiveButton(getString(R.string.restart_text)) { dialog, which -> openMoviesListFragment() }
        .setNegativeButton(getString(R.string.cancel_text)) { dialog, which -> finish() }
        .create().show()

    companion object {
        private const val DURATION_FOR_LIST_MOVIES: Long = 200
        private const val DURATION_FOR_MOVIE: Long = 400
        private const val FRAGMENT_MOVIE = "movie"
    }
}

class DetailsTransition : TransitionSet() {

    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
            .addTransition(ChangeTransform())
            .addTransition(ChangeImageTransform())
    }
}
