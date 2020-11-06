package ru.petrgostev.myfirstproject

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.os.Handler
import java.text.SimpleDateFormat
import java.util.*

const val TEXT_DETECTED = "ru.petrgostev.myfirstproject.action.TEXT"

class MyIntentService : IntentService("MyIntentService") {
    companion object {
        const val TEXT: String = "TEXT"
    }

    override fun onHandleIntent(intent: Intent?) {
        val time: Int = intent?.getIntExtra("time", 0) ?: 0
        { sendBroadcast(Intent(TEXT_DETECTED).putExtra(TEXT, getCurrentDate())) }.withDelay(time.toLong())
    }

    fun <R> (() -> R).withDelay(delay: Long) {
        Handler().postDelayed({ this.invoke() }, delay)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate():String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}