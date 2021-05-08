package ru.petrgostev.myfirstproject.data.network.pojo

import org.junit.Test

import org.junit.Assert.*
import ru.petrgostev.myfirstproject.data.dataBase.entity.GenresEntity

class GenresItemTest {

    @Test
    fun toGenresEntity() {
        val genresItem = GenresItem("a", 1)
        val genresEntity = GenresEntity(name = genresItem.name, id = genresItem.id.toLong())

        assertEquals(genresItem.name, genresEntity.name)
        assertEquals(genresItem.id.toLong(), genresEntity.id)
    }
}