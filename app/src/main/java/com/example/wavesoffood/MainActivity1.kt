package com.example.wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wavesoffood.databinding.ActivityMain1Binding
import com.example.wavesoffood.databinding.FragmentNotificationBottomBinding
import com.example.wavesoffood.databinding.NotificationItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityMain1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var NavController:NavController=findNavController(R.id.fragmentContainerView)
        var bottomnav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomnav.setupWithNavController(NavController)
        binding.notificationButton.setOnClickListener {
            val bottomSheetDialog= NotificationBottomFragment()
            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
    }
}