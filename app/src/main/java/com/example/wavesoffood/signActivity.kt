package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wavesoffood.databinding.ActivitySignBinding
import com.example.wavesoffood.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class signActivity<GoogleSignInClient> : AppCompatActivity() {

    private lateinit var email :String
    private lateinit var password :String
    private lateinit var username  :String
    private lateinit var auth : FirebaseAuth
    private lateinit var database: DatabaseReference
   // private lateinit var googleSignInClient: GoogleSignInClient



    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth= Firebase.auth
        database=Firebase.database.reference

        binding.creatAccountButton.setOnClickListener {
            username=binding.userName.text.toString()
            email=binding.emailAddress.text.toString().trim()
            password=binding.password.text.toString().trim()

            if(email.isEmpty()|| password.isBlank()|| username.isBlank()){
                Toast.makeText(this,  "Please Fill the Data",Toast.LENGTH_SHORT).show()
            }else{
                creatAccount(email,password)
            }
        }

        binding.alreadyhaveaccount.setOnClickListener {
            val intent=Intent(this,LoginActivity5::class.java)
            startActivity(intent)
        }
    }

    private fun creatAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Account Created Succesfully 😊",Toast.LENGTH_SHORT).show()
                saveUserData()
                startActivity(Intent(this,LoginActivity5::class.java))
                finish()
            }else{
                Toast.makeText(this,"Account Creation Failed 😒 ",Toast.LENGTH_SHORT).show()
                Log.d("Account ","Create Account : Failure ",task.exception)
            }
        }
    }

    private fun saveUserData() {
        username=binding.userName.text.toString()
        password=binding.password.text.toString().trim()
        email=binding.emailAddress.text.toString().trim()

        val user =UserModel(username,email,password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        database.child("user").child(userId).setValue(user)

    }
}