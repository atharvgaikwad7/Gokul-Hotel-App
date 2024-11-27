package com.example.wavesoffood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityDetailsBinding
import com.example.wavesoffood.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private var foonName: String? = "Sandwich"
    private var foodImage: String? = null
    private var foodDescription: String? = null
    private var foodIngredients: String? = null
    private var foodPrice:Int? = 100
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Get data passed from previous activity
        val foodName = intent.getStringExtra("MenuItemName")
        val foodImage = intent.getIntExtra("MenuItemImage", 0)

        binding.detailFoodName.text = foodName
        binding.detailFoodImage.setImageResource(foodImage)

        // Back button click listener
        binding.backarrowbutton.setOnClickListener {
            // Redirect to MainActivity1 (home page)
            val intent = Intent(this, MainActivity1::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // Clear all previous activities on top of MainActivity1
            startActivity(intent)
        }

        // Add item to cart button click listener
        binding.AddItemButton.setOnClickListener {
            addItemToCart()
        }
    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid ?: ""

        val cartItems = CartItems(foonName.toString(), foodPrice.toString(), foodDescription.toString(), foodImage.toString(), 1)

        database.child("user").child(userId).child("cartItems").push().setValue(cartItems).addOnSuccessListener {
            Toast.makeText(this, "Item Added to Cart Successfully 😁", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Item Not Added 😒 ", Toast.LENGTH_SHORT).show()
        }
    }
}
