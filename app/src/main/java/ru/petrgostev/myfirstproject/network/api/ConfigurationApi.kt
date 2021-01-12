package ru.petrgostev.myfirstproject.network.api

import retrofit2.http.GET
import ru.petrgostev.myfirstproject.network.pojo.ConfigurationResponse

interface ConfigurationApi {
    
    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationResponse
}