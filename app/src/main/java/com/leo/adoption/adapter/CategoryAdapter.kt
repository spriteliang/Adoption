package com.leo.adoption.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.leo.adoption.R
import com.leo.adoption.data.DataCategories

class CategoryAdapter : Adapter<CategoryAdapter.CategoryViewHolder>() {
    var dataList = emptyList<DataCategories>()

    internal fun setDataList(dataList: List<DataCategories>) {
        this.dataList = dataList
    }

    inner class CategoryViewHolder(itemView: View) : ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.image_category)
        var name: TextView = itemView.findViewById(R.id.text_category_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Get the data model based on position
        var data = dataList[position]

        holder.name.text = data.name
        holder.image.setImageResource(data.image)
    }
}
