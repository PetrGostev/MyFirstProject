package ru.petrgostev.myfirstproject.utils

import android.content.Context
import android.net.ConnectivityManager

object Connect{
    fun isConnect(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
       val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}