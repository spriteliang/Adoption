package com.leo.adoption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.adoption.databinding.FavoriteItemBinding
import com.leo.adoption.pojo.Adoption

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoritesAdapterViewHolder>()  {
    lateinit var onItemClick: ((Adoption) -> Unit)


    inner class FavoritesAdapterViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Adoption>() {
        override fun areItemsTheSame(oldItem: Adoption, newItem: Adoption): Boolean {
            return oldItem.animal_id == newItem.animal_id
        }

        override fun areContentsTheSame(oldItem: Adoption, newItem: Adoption): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapterViewHolder {
        return FavoritesAdapterViewHolder(
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        val adoption = differ.currentList[position]
        Glide.with(holder.itemView).load(adoption.album_file).into(holder.binding.favImg)
        holder.binding.favAddress.text = adoption.animal_place
        holder.itemView.setOnClickListener {
            onItemClick.invoke(adoption)
        }
    }

}