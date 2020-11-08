package ru.petrgostev.myfirstproject

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Handler
import java.text.SimpleDateFormat
import java.util.*

class MyIntentService : IntentService("MyIntentService") {
    companion object {
        const val TEXT_DETECTED = "ru.petrgostev.myfirstproject.action.TEXT"
        const val DELAY_TIME: String = "DELAY_TIME"
        const val TEXT: String = "TEXT"

        fun createIntent(context: Context, time: Int): Intent =
            Intent(context, MyIntentService::class.java)
                .putExtra(DELAY_TIME, 3)
    }

    override fun onHandleIntent(intent: Intent?) {
        val time: Int = intent?.getIntExtra(DELAY_TIME, 0) ?: 0
        {
            sendBroadcast(
                Intent(TEXT_DETECTED).putExtra(
                    TEXT,
                    getCurrentDate()
                )
            )
        }.withDelay(time.toLong())
    }

    fun <R> (() -> R).withDelay(delay: Long) {
        Handler().postDelayed({ this.invoke() }, delay)
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.US)
        return sdf.format(Date())
    }
}