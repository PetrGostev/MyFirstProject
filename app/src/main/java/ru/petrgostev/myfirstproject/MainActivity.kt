package ru.petrgostev.myfirstproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.petrgostev.myfirstproject.ui.moviesDetails.MoviesDetailsFragment
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesListFragment
import ru.petrgostev.myfirstproject.utils.*

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

    override fun openMoviesDetailsFragment(movieId: Int) {
        if (!Connect.isConnected) {
            ToastUtil.showToastNotConnected(this)
            return
        }

        Handler().postDelayed({
            supportFragmentManager.popBackStack(
                FRAGMENT_MOVIE,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

            supportFragmentManager.commit {
                addToBackStack(FRAGMENT_MOVIE)
                replace(R.id.fame, MoviesDetailsFragment.newInstance(movieId))
            }
        }, DURATION_FOR_MOVIE)
    }

    private fun openMoviesDetailsFragmentFromIntent(intent: Intent?) {
        intent?.let {
            val id = intent.data?.lastPathSegment?.toLongOrNull()
            id?.let { openMoviesDetailsFragment(id.toInt()) }
        }
    }

    private fun openMoviesListFragment() {
        if (!Connect.isConnected) {
            showNetworkErrorDialog()
            return
        }
//        Handler().postDelayed({
                supportFragmentManager.beginTransaction()
                    .add(R.id.fame, MoviesListFragment())
                    .commit()
//        }, DURATION_FOR_LIST_MOVIES)
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