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
    var posterSizes:List<String>? = emptyList<String>()
}

enum class PosterSizeEnum(val size: String?){
    W45(PosterSizeList.posterSizes?.get(0)),
    W92(PosterSizeList.posterSizes?.get(1)),
    W154(PosterSizeList.posterSizes?.get(2)),
    W185(PosterSizeList.posterSizes?.get(3)),
    W300(PosterSizeList.posterSizes?.get(4)),
    W500(PosterSizeList.posterSizes?.get(5)),
    ORIGINAL(PosterSizeList.posterSizes?.get(6))
}

enum class Category {
    POPULAR, TOP_RATED, UPCOMING
}
