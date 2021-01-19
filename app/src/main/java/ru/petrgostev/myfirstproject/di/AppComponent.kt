package ru.petrgostev.myfirstproject.di

import dagger.Component
import ru.petrgostev.myfirstproject.network.api.NetworkModule

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getNetworkModule(): NetworkModule
}