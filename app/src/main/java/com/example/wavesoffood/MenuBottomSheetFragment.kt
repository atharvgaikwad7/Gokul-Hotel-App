package com.example.wavesoffood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.adaptar.MenuAdapter
import com.example.wavesoffood.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater, container, false)

        // Back button to dismiss the bottom sheet
        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        // Menu data
        val menuFoodname = listOf("Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo", "Burger", "Sandwich", "Momo", "Item", "Sandwich", "Momo")
        val menuItemPrice = listOf("100rs", "150rs", "200rs", "50rs", "100rs", "150rs", "100rs", "150rs", "200rs", "50rs", "100rs", "150rs")
        val menuImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6
        )
        if (menuFoodname.size == menuItemPrice.size && menuItemPrice.size == menuImage.size) {
            val adapter = MenuAdapter(
                ArrayList(menuFoodname),
                ArrayList(menuItemPrice),
                ArrayList(menuImage),requireContext()
            )

        // Check for data consistency
//        if (menuFoodname.size == menuItemPrice.size && menuItemPrice.size == menuImage.size) {
//            val adapter = MenuAdapter(
//                menuFoodname,
//                menuItemPrice,
//                menuImage,
//                requireContext() // Pass context here
//            )

            // Setup RecyclerView
            binding.menuRecylerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecylerView.adapter = adapter
        } else {
            // Handle the error (e.g., show a message or log an error)
            println("Error: Menu data lists are not consistent in size")
        }

        return binding.root
    }

    companion object {
        // Optional companion object if needed
    }
}
