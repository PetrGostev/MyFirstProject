package ru.petrgostev.myfirstproject.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.utils.Adult
import ru.petrgostev.myfirstproject.utils.GenresMap

@Serializable
class MoviesResponse(

    @SerialName("page")
    val page: Int,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("results")
    var movieResponses: List<MoviesItem> = emptyList(),
)

@Serializable
data class MoviesItem(

    @SerialName("overview")
    val overview: String?,

    @SerialName("title")
    val title: String?,

    @SerialName("genre_ids")
    val genreIds: List<Int>?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("popularity")
    val popularity: Double?,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("id")
    val id: Int?,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("vote_count")
    val voteCount: Int?
) {
    val rating_5: Float = (voteAverage / 2).toFloat()
    val minimumAge: Int = if (adult) Adult.ADULT else Adult.NOT_ADULT
    val genre: () -> String = {
        val genres = mutableListOf<String>()
        genreIds?.forEach {
            genres.add(GenresMap.genres.getValue(it))
        }
        genres.joinToString()
    }
}
