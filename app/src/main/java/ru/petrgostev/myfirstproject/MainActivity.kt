package ru.petrgostev.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.petrgostev.myfirstproject.moviesList.MoviesListFragment
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.moviesDetails.MoviesDetailsFragment

class MainActivity : AppCompatActivity(), Router {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fame, MoviesListFragment())
                .commit()
        }
    }

    override fun openMoviesDetailsFragment(movie: Movie) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fame, MoviesDetailsFragment.newInstance(movie))
            .addToBackStack("MoviesDetailsFragment")
            .commit()
    }
}