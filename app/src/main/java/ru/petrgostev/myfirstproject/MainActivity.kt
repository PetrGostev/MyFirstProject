package ru.petrgostev.myfirstproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            addFragments()
        }
    }

    private fun addFragments() {
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().apply {
            add(R.id.fame, MoviesListFragment())
            commit()
        }

        fragmentManager.beginTransaction().apply {
            replace(R.id.fame, MoviesDetailsFragment())
            addToBackStack("MoviesDetailsFragment")
            commit()
        }
    }
}