package ru.petrgostev.myfirstproject.MoviesList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.MoviesList.adapter.MovieViewsAdapter
import ru.petrgostev.myfirstproject.data.Actor
import ru.petrgostev.myfirstproject.data.Movie
import ru.petrgostev.myfirstproject.databinding.FragmentMoviesListBinding
import ru.petrgostev.myfirstproject.moviesDetails.MoviesDetailsFragment

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private var viewBinding: FragmentMoviesListBinding? = null
    private lateinit var adapter: MovieViewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentMoviesListBinding.bind(view)

        adapter = MovieViewsAdapter(requireContext(), object :
            MovieViewsAdapter.OnMovieClickListener {
            override fun onClick(movie: Movie) {
                showMoviesDetailsFragment(movie)
            }
        })

        viewBinding!!.moviesRecycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        updateData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun updateData() {
        adapter.submitList(getMovies())
    }

    private fun showMoviesDetailsFragment(movie: Movie) {
        val supportFragmentManager = activity?.supportFragmentManager
        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fame, MoviesDetailsFragment.newInstance(movie))
            ?.addToBackStack("MoviesDetailsFragment")
            ?.commit()
    }

    private fun getMovies(): List<Movie> {
        val movieList: ArrayList<Movie> = arrayListOf()
        movieList.apply {
            add(
                Movie(
                    "Вурдалаки",
                    160,
                    500,
                    4f,
                    "Ужасы, триллер",
                    true,
                    18,
                    R.drawable.movie,
                    getActors()
                )
            )
            add(
                Movie(
                    "Дракула",
                    190,
                    900,
                    5f,
                    "Ужасы, триллер",
                    false,
                    18,
                    R.drawable.movie_2,
                    getActors()
                )
            )
            add(
                Movie(
                    "День в Париже",
                    140,
                    300,
                    2f,
                    "Мелодрама, триллер",
                    true,
                    12,
                    R.drawable.movie_3,
                    getActors()
                )
            )
            add(
                Movie(
                    "Месть невидимки",
                    160,
                    900,
                    3f,
                    "Фаетастика, боевик",
                    false,
                    12,
                    R.drawable.movie_4,
                    getActors()
                )
            )
        }
        return movieList
    }

    private fun getActors(): List<Actor> {
        var actors: ArrayList<Actor> = arrayListOf()

        actors.apply {
            add(
                Actor("Robert Downey Jr.", R.drawable.actor)
            )
            add(
                Actor("Анджелина Джолли", R.drawable.actor_2)
            )
            add(
                Actor("Джони Деб", R.drawable.actor_3)
            )
            add(
                Actor("Михаил Боярский", R.drawable.actor_4)
            )
            add(
                Actor("Михаил Ефремов", R.drawable.actor)
            )
        }

        return actors
    }
}