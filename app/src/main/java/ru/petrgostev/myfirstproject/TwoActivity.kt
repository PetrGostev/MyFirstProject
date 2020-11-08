package ru.petrgostev.myfirstproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TwoActivity : AppCompatActivity() {
    companion object {
        const val TEXT_DETECTED = "ru.petrgostev.myfirstproject.action.TEXT"
        const val TEXT: String = "TEXT"

        fun createIntent(context: Context): Intent =
            Intent(context, TwoActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        val myIntentService = MyIntentService.createIntent(this, 3)
        startService(myIntentService)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(localBroadcastReceiver, IntentFilter(TEXT_DETECTED));
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(localBroadcastReceiver)
    }

    private val localBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val newIntent = Intent(context, MainActivity::class.java)
            newIntent.putExtra(TEXT, intent?.getStringExtra(TEXT))
            startActivity(newIntent)
        }
    }
}