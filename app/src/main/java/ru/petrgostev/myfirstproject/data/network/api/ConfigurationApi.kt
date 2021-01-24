package ru.petrgostev.myfirstproject.data.network.api

import retrofit2.http.GET
import ru.petrgostev.myfirstproject.data.network.pojo.ConfigurationResponse

interface ConfigurationApi {
    
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}