package ru.petrgostev.myfirstproject.di

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        component = DaggerAppComponent.create()
    }
}