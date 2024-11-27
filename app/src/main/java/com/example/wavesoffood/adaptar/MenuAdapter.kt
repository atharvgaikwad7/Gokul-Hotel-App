package com.example.wavesoffood.adaptar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.MenuItemBinding

class MenuAdapter(
    private val menuItemsName: List<String>,
    private val menuItemsPrice: List<String>,
    private val menuImages: List<Int>,
    private val context: Context // Pass context here
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuItemsName.size

    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("MenuItemName", menuItemsName[position])
                    intent.putExtra("MenuItemImage", menuImages[position])
                    context.startActivity(intent)
                }
            }
        }

        fun bind(position: Int) {
            binding.apply {
                menuFoodName.text = menuItemsName[position] // Ensure menuFoodName exists in layout
                menuPrice.text = menuItemsPrice[position]  // Ensure menuPrice exists in layout
                menuImage.setImageResource(menuImages[position]) // Ensure menuImage exists in layout
            }
        }
    }
}
