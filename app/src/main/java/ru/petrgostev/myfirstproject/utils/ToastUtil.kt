package ru.petrgostev.myfirstproject.utils

import android.content.Context
import android.widget.Toast
import ru.petrgostev.myfirstproject.R

object ToastUtil {
    fun showToastNotConnected(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.no_connecting),
            Toast.LENGTH_LONG
        ).show()
    }

    fun showToastNoConnectionYet(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.no_connection_yet),
            Toast.LENGTH_LONG
        ).show()
    }
}