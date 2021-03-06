package ru.petrgostev.myfirstproject.data.network.pojo

import org.junit.Assert.assertEquals
import org.junit.Test

class MoviesItemTest {

    @Test
    fun startKeys() {
        getMoviePoster()
        getRating_5()
        getMinimumAge()
        getGenre()
    }

    @Test
    fun getMoviePoster() {
        assertEquals(
            "https://api.themoviedb.org/3/W500/1234321432.jpeg",
            "https://api.themoviedb.org/3/" + "W500" + "/1234321432.jpeg"
        )
    }

    @Test
    fun getRating_5() {
        assertEquals(5, 10 / 2)
    }

    @Test
    fun getMinimumAge() {
        var adult = true
        var limitAdult = if (adult) 18 else 0
        assertEquals(18, limitAdult)

        adult = false
        limitAdult = if (adult) 18 else 0
        assertEquals(0, limitAdult)
    }

    @Test
    fun getGenre() {
        val genresMap = mutableMapOf<Int, String>()
        genresMap[1] = "Триллер"
        genresMap[2] = "Драма"
        genresMap[3] = "Боевик"
        genresMap[4] = "Ужасы"
        genresMap[5] = "Мелодрама"

        val genreIds = mutableListOf<Int>()
        genreIds.add(1)
        genreIds.add(5)

        val genres = mutableListOf<String>()
        genreIds.forEach {
            genres.add(genresMap.getValue(it))
        }

        assertEquals("Триллер, Мелодрама", genres.joinToString())
    }
}