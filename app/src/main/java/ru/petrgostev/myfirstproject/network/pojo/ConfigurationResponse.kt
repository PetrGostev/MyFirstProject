package ru.petrgostev.myfirstproject.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
)
