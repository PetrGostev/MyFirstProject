package ru.petrgostev.myfirstproject.data.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.petrgostev.myfirstproject.utils.ApiKey
import ru.petrgostev.myfirstproject.utils.BaseUrl
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(API_KEY_NAME, ApiKey.API_KEY_VALUE)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(newRequest)
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
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

    @Provides
    fun getNetworkModule(): NetworkModule = this

    val genreApi: GenreApi = retrofit.create()
    val moviesApi: MoviesApi = retrofit.create()
    val configurationApi: ConfigurationApi = retrofit.create()

    companion object {
        private const val API_KEY_NAME = "api_key"
        private const val READ_TIMEOUT: Long = 20
        private const val WRITE_TIMEOUT: Long = 20
        private const val CONNECT_TIMEOUT: Long = 10
    }
}