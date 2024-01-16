package com.leo.adoption.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.leo.adoption.databinding.CategoryItemBinding
import com.leo.adoption.pojo.Category


class CategoryAdapter :
    Adapter<CategoryAdapter.CategoryViewHolder>() {
    private lateinit var categoryList: ArrayList<Category>
    var onItemClick: ((Category) -> Unit)? = null
    var selectedPosition = -1
    fun setCategoryList(categoryList: ArrayList<Category>) {
        this.categoryList = categoryList
    }

    class CategoryViewHolder(var binding: CategoryItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (selectedPosition === position) {
            holder.binding.categoryImage.setBackgroundColor(
                Color.parseColor("#f52c56")
            )
        } else {
            holder.binding.categoryImage.setBackgroundColor(
                Color.parseColor( "#E6E6E6")
            )
        }
        holder.binding.categoryName.text = categoryList[position].name
        Glide.with(holder.itemView)
            .load(categoryList[position].categoryImage)
            .into(holder.binding.categoryImage)
        holder.itemView.setOnClickListener {
            selectedPosition = position
            onItemClick?.invoke(categoryList[position])
            notifyDataSetChanged()
        }
    }
}