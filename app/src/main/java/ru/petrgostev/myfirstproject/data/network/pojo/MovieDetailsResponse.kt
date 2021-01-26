package ru.petrgostev.myfirstproject.data.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.utils.Adult
import ru.petrgostev.myfirstproject.utils.ImagesBaseUrl
import ru.petrgostev.myfirstproject.utils.PosterSizeEnum

@Serializable
class MovieDetailsResponse(

    @SerialName("original_language")
    val originalLanguage: String,

    @SerialName("video")
    val video: Boolean,

    @SerialName("title")
    val title: String,

    @SerialName("backdrop_path")
    val backdropPath: String,

    @SerialName("genres")
    val genres: List<GenresItem>,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("id")
    val id: Int,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("overview")
    val overview: String,

    @SerialName("runtime")
    val runtime: Int,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("tagline")
    val tagLine: String,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("homepage")
    val homepage: String,

    @SerialName("status")
    val status: String
) {
    val moviePoster:String = ImagesBaseUrl.IMAGES_BASE_URL + PosterSizeEnum.W500.size + posterPath
    val rating_5: Float = (voteAverage / 2).toFloat()
    val minimumAge: Int = if (adult) Adult.ADULT else Adult.NOT_ADULT
}

@Serializable
class GenresForDetails(

    @SerialName("name")
    val name: String,

    @SerialName("id")
    val id: Int
)