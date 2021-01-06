package ru.petrgostev.myfirstproject.utils

object Connect {
    var isConnected = true
}

object GenresMap {
    val genres = mutableMapOf<Int, String>()
}

object ImagesBaseUrl {
    var IMAGES_BASE_URL = ""
}

object PosterSizeList {
    var posterSizes = emptyList<String>()
}

enum class PosterSizeEnum(val size: String){
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
