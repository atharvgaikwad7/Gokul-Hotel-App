package com.example.wavesoffood.Fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.PayOutActivity
import com.example.wavesoffood.adapter.CartAdapter
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.example.wavesoffood.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames: MutableList<String>
    private lateinit var foodPrices: MutableList<String>
    private lateinit var foodDescriptions: MutableList<String>
    private lateinit var foodImagesUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        retrieveCartItems()

        binding.proccedButton.setOnClickListener {
            getOrderItemDetails()
            val intent = Intent(requireContext(), PayOutActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun getOrderItemDetails() {
        val oerderIdReference:DatabaseReference=database.reference.child("user").child(userId).child("cartItems")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()

        val foodQuantities = cartAdapter.getUpdatedItemsQuantities()

        oerderIdReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (foodSnapshot in snapshot.children){
                     val orderItems =foodSnapshot.getValue(CartItems::class.java)

                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredint?.let { foodIngredient.add(it) }
                }
                orderNow(foodName,foodPrice,foodDescription,foodImage,foodIngredient,foodQuantities)

            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(requireContext(),"Order Making Failed.Please Try Again ", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities: MutableList<Int>
    ) {

        if (isAdded && context!= null){
            val intent =Intent(requireContext(),PayOutActivity::class.java)
            intent.putExtra("FoodItemName",foodName as ArrayList<String>)
            intent.putExtra("FoodItemPrice ",foodPrice as ArrayList<String>)
            intent.putExtra("FoodItemImage",foodImage as ArrayList<String>)
            intent.putExtra("FoodItemDescription",foodDescription as ArrayList<String>)
            intent.putExtra("FoodItemIngredient ",foodIngredient as ArrayList<String>)
            intent.putExtra("FoodItemQuantities ",foodQuantities as ArrayList<Int>)
            startActivity(intent)
        }

    }

    private fun retrieveCartItems() {
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("cartItems")
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodImagesUri = mutableListOf()
        foodIngredients = mutableListOf()
        quantity = mutableListOf()

        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    val cartItem = foodSnapshot.getValue(CartItems::class.java)

                    cartItem?.foodName?.let { foodNames.add(it) }
                    cartItem?.foodPrice?.let { foodPrices.add(it) }
                    cartItem?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItem?.foodImage?.let { foodImagesUri.add(it) }
                    cartItem?.foodQuantity?.let { quantity.add(it) }
                    cartItem?.foodIngredint?.let { foodIngredients.add(it) }
                }

                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetched: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun setAdapter() {
        cartAdapter = CartAdapter(
            requireContext(),
            foodNames,
            foodPrices,
            foodDescriptions,
            foodImagesUri,
            quantity,
            foodIngredients
        )
        binding.cartRecycleview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.cartRecycleview.adapter = cartAdapter
    }
}
