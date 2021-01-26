package ru.petrgostev.myfirstproject

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
        // Делаем задержку, чтобы успело провериться подключение
        Handler().postDelayed({
            if (Connect.isConnected) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fame, MoviesListFragment())
                    .commit()
            } else {
                showNetworkErrorDialog()
            }
        }, DURATION)
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
        private const val DURATION:Long = 200
    }
}