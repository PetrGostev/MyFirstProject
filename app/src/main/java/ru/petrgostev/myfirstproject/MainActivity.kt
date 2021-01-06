package ru.petrgostev.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.petrgostev.myfirstproject.moviesList.MoviesListFragment
import ru.petrgostev.myfirstproject.moviesDetails.MoviesDetailsFragment
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
        supportFragmentManager.beginTransaction()
            .add(R.id.fame, MoviesListFragment())
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