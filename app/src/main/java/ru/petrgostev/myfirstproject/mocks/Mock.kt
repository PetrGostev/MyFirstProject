package ru.petrgostev.myfirstproject.mocks

import ru.petrgostev.myfirstproject.R
import ru.petrgostev.myfirstproject.data.Actor
import ru.petrgostev.myfirstproject.data.Movie

class Mock {
    fun getMovies(): List<Movie> {
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
        val actors: ArrayList<Actor> = arrayListOf()

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