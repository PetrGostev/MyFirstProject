package ru.petrgostev.myfirstproject.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.petrgostev.myfirstproject.network.api.GenreApi
import ru.petrgostev.myfirstproject.network.api.MoviesApi
import ru.petrgostev.myfirstproject.utils.BaseUrl
import java.util.concurrent.TimeUnit

class NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl.BASE_URL)
        .client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val genreApi:GenreApi = retrofit.create()
    val moviesApi:MoviesApi = retrofit.create()
}