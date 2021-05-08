package ru.petrgostev.myfirstproject.data.network.pojo

import org.junit.Test

import org.junit.Assert.*

class MovieDetailsResponseTest {

    @Test
    fun startKeys() {
        getMoviePoster()
        getRating_5()
        getMinimumAge()
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
}