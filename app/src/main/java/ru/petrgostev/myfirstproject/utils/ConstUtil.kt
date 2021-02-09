package ru.petrgostev.myfirstproject.utils

 object BaseUrl{
    const val BASE_URL= "https://api.themoviedb.org/3/"
}

object ApiKey{
    const val API_KEY_VALUE= "ab3d7335ac128bcec0b0a8926034daa4"
}

object DateFormat{
    const val DATE_FORMAT= "dd/M/yyyy\""
}

object Language{
    const val LANGUAGE_RU= "ru"
    const val LANGUAGE_EN= "en"
}

object Adult{
    const val ADULT= 18
    const val NOT_ADULT= 0
}

object Page{
    var PAGE_SIZE = 20
    var STARTING_PAGE = 1
    var TWO_PAGE = 2
}
