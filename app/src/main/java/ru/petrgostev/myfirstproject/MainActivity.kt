package ru.petrgostev.myfirstproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ru.petrgostev.myfirstproject.moviesDetails.MoviesDetailsFragment
import ru.petrgostev.myfirstproject.moviesList.MoviesListFragment
import ru.petrgostev.myfirstproject.utils.*

class MainActivity : AppCompatActivity(), Router {

    private val networkMonitor = NetworkMonitorUtil(this)
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNetworkMonitor()

        bundle = savedInstanceState

        openMoviesListFragment()
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    override fun openMoviesDetailsFragment(movieId: Int) {
        if (!Connect.isConnected) {
            ToastUtil.showToastNotConnected(this)
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fame, MoviesDetailsFragment.newInstance(movieId))
            .addToBackStack(MoviesDetailsFragment::class.java.name)
            .commit()
    }

    private fun openMoviesListFragment() {
        if (bundle == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fame, MoviesListFragment())
                .commit()
        }
    }

    private fun initNetworkMonitor() {
        networkMonitor.result = { isAvailable, type ->
            runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Connect.isConnected = true
                                openMoviesListFragment()
                            }
                            ConnectionType.Cellular -> {
                                Connect.isConnected = true
                                openMoviesListFragment()
                            }
                            else -> {
                            }
                        }
                    }
                    false -> {
                        Connect.isConnected = false
                        ToastUtil.showToastNotConnected(this)
                    }
                }
            }
        }
    }
}