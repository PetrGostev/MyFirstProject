package ru.petrgostev.myfirstproject.data.network.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.ui.moviesList.MoviesViewItem
import ru.petrgostev.myfirstproject.utils.*

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
    val id: Int,

    @SerialName("adult")
    val adult: Boolean,

    @SerialName("vote_count")
    val voteCount: Int?
) {
    val moviePoster:String = ImagesBaseUrl.IMAGES_BASE_URL + PosterSizeEnum.W500.size + posterPath
    val rating_5: Float = (voteAverage / 2).toFloat()
    val minimumAge: Int = if (adult) Adult.ADULT else Adult.NOT_ADULT

    fun getGenre(): String {
        val genres = mutableListOf<String>()
        genreIds?.forEach {
            genres.add(GenresMap.genres.getValue(it))
        }

        return genres.joinToString()
    }

    fun toMoviesViewItem() = MoviesViewItem(
        id = id,
        overview = overview ?: "",
        title = title ?: "",
        popularity = popularity ?: 0.0,
        voteAverage = voteAverage,
        voteCount = voteCount ?: 0,
        moviePoster = moviePoster,
        rating_5 = rating_5,
        minimumAge = minimumAge,
        genre = getGenre()
    )

    fun toMoviesEntity(category: Category) = MoviesEntity(
        id = id.toLong(),
        overview = overview ?: "",
        title = title ?: "",
        popularity = popularity ?: 0.0,
        voteAverage = voteAverage,
        voteCount = voteCount ?: 0,
        moviePoster = moviePoster,
        rating_5 = rating_5,
        minimumAge = minimumAge,
        genre = getGenre(),
        isPopular = category == Category.POPULAR,
        isTopRated = category == Category.TOP_RATED,
        isUpcoming = category == Category.UPCOMING,
    )
}
