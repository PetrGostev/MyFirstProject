package ru.petrgostev.myfirstproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import ru.petrgostev.myfirstproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val TEXT: String = "TEXT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.textView.text = intent.getStringExtra(TEXT)
        binding.startTwoActivity.setOnClickListener { startTwoActivity() }
    }

    private fun startTwoActivity() {
        val newIntent = TwoActivity.createIntent(this)
        startActivity(newIntent)
    }
}