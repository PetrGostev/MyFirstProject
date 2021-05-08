package ru.petrgostev.myfirstproject.data.network.pojo

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity

class ImagesResponseTest {

    @Test
    fun toImagesEntity() {
        val imagesResponse = ImagesResponse(emptyList(), "")
        val imagesEntity = ImagesEntity(
            posterSizes = imagesResponse.posterSizes,
            secureBaseUrl = imagesResponse.secureBaseUrl
        )

        assertEquals(imagesResponse.posterSizes, imagesEntity.posterSizes)
        assertEquals(imagesResponse.secureBaseUrl, imagesEntity.secureBaseUrl)
    }
}