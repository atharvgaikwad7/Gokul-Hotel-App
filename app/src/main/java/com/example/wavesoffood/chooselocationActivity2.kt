package com.example.wavesoffood

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityChooselocation2Binding

class ChooselocationActivity2 : AppCompatActivity() {
    private val binding: ActivityChooselocation2Binding by lazy {
        ActivityChooselocation2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val locationList= arrayOf("Rajur","Shendi","Shenit","Maveshi")
        val adapter =ArrayAdapter(this,android.R.layout.simple_list_item_1,locationList)
        val autoCompleteTextView=binding.listofLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}
