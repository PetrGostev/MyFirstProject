package ru.petrgostev.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    companion object {
        const val TEXT: String = "TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        textView.text = intent.getStringExtra(TEXT)

        val startTwoActivityButton: Button = findViewById(R.id.start_two_activity)
        startTwoActivityButton.setOnClickListener { startTwoActivity() }
    }

    private fun startTwoActivity() {
        intent = Intent(this, TwoActivity::class.java)
        startActivity(intent)
    }
}