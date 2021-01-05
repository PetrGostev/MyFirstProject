package ru.petrgostev.myfirstproject.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.utils.GenresMap

@Serializable
data class MoviesResponse(

    @SerialName("page")
    val page: Int,

    @SerialName("total_pages")
    val totalPages: Int,

    @SerialName("results")
    val movieResponses: List<MoviesItem>,
)

@Serializable
data class MoviesItem(

    @SerialName("overview")
    val overview: String,

    @SerialName("title")
    val title: String,

    @SerialName("genre_ids")
    val genreIds: List<Int>,

    @SerialName("poster_path")
    val posterPath: String,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("id")
    val id: Int,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("vote_count")
    val voteCount: Int
) {
    val rating_5: Float = (voteAverage / 2).toFloat()
    val minimumAge: Int = if (adult) 16 else 13
    val genre: () -> String = {
        val genres = mutableListOf<String>()
        genreIds.forEach {
            genres.add(GenresMap.genres.getValue(it))
        }
        genres.joinToString()
    }
}
