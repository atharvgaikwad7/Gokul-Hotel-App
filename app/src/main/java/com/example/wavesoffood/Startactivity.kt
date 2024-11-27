package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityStartactivityBinding

class Startactivity : AppCompatActivity() {
    private val binding: ActivityStartactivityBinding by lazy {
        ActivityStartactivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            val intent = Intent(this,LoginActivity5::class.java) // Replace with the correct Activity class
            startActivity(intent)
        }
    }
}
