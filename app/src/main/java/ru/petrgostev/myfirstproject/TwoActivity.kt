package ru.petrgostev.myfirstproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TwoActivity : AppCompatActivity() {
    companion object {
        const val TEXT: String = "TEXT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        val myIntentService = Intent(this, MyIntentService::class.java)
        startService(myIntentService.putExtra("time", 3))
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