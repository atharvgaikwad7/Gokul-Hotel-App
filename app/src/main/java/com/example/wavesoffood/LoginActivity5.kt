package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityLoginactivity5Binding
import com.example.wavesoffood.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.util.Log

class LoginActivity5 : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivityLoginactivity5Binding by lazy {
        ActivityLoginactivity5Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Login button click listener
        binding.loginButton.setOnClickListener {
            email = binding.emailAddress2.text.toString().trim()
            password = binding.password1.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter all details 😒", Toast.LENGTH_SHORT).show()
            } else {
                loginUser()
            }
        }

        // "Don't have an account?" button click listener
        binding.donthavebutton.setOnClickListener {
            val intent = Intent(this, signActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        // Check if the user is already logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is logged in, update the UI
            updateUi(currentUser)
        }
    }

    private fun loginUser() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(this, "Login Successful 😁", Toast.LENGTH_SHORT).show()
                updateUi(user)
            } else {
                // Log the error for debugging
                Log.e("LoginError", "Login failed", task.exception)
                Toast.makeText(
                    this,
                    "Login failed. Please check your credentials or sign up.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUi(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(this, MainActivity1::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Failed to update UI. User is null.", Toast.LENGTH_SHORT).show()
        }
    }
}
