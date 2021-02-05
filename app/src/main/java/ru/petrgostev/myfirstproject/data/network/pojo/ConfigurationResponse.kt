package ru.petrgostev.myfirstproject.data.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.data.dataBase.entity.ImagesEntity

@Serializable
class ConfigurationResponse(

    @SerialName("images")
    val images: ImagesResponse,
)

@Serializable
class ImagesResponse(

    @SerialName("poster_sizes")
    val posterSizes: List<String>,

    @SerialName("secure_base_url")
    val secureBaseUrl: String,
){
    fun toImagesEntity() = ImagesEntity(posterSizes = posterSizes, secureBaseUrl = secureBaseUrl)
}
