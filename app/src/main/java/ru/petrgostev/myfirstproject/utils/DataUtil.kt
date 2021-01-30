package ru.petrgostev.myfirstproject.utils

import java.text.SimpleDateFormat
import java.util.*

object Connect {
    var isConnected = true
}

object GenresMap {
    val genres = mutableMapOf<Int, String>()
}

object ImagesBaseUrl {
    var IMAGES_BASE_URL = ""
}

object MoviesDate {
    var IS_RELEVANT_UPDATE_DATE = false
    val FORMAT_DATE = SimpleDateFormat(DateFormat.DATE_FORMAT, Locale(Language.LANGUAGE_RU))
}

object PosterSizeList {
    var posterSizes:List<String> = emptyList<String>()
}

enum class PosterSizeEnum(val size: String?){
    W45(PosterSizeList.posterSizes[0]),
    W92(PosterSizeList.posterSizes[1]),
    W154(PosterSizeList.posterSizes[2]),
    W185(PosterSizeList.posterSizes[3]),
    W300(PosterSizeList.posterSizes[4]),
    W500(PosterSizeList.posterSizes[5]),
    ORIGINAL(PosterSizeList.posterSizes[6])
}

enum class Category {
    POPULAR, TOP_RATED, UPCOMING
}
