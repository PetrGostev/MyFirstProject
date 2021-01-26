package ru.petrgostev.myfirstproject.data.repository

import ru.petrgostev.myfirstproject.di.App

class RepositoriesFacade() {
    val dataBaseRepository: DataBaseRepositoryInterface by lazy { DataBaseRepository() }
    val networkRepository: NetworkRepositoryInterface by lazy { NetworkRepository(App.component.getNetworkModule()) }
}