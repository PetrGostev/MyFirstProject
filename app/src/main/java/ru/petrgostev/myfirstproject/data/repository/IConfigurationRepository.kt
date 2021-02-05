package ru.petrgostev.myfirstproject.data.repository

interface IConfigurationRepository {
    suspend fun checkUpdateDate()
}