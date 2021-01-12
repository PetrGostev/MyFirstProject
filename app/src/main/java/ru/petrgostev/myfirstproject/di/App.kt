package ru.petrgostev.myfirstproject.di

import android.app.Application

class App : Application() {
//    private lateinit var component: AppComponent

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    fun getComponent(): AppComponent {
        return component
    }
}