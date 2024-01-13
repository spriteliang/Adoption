package com.leo.adoption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.leo.adoption.databinding.AdoptionDetailItemBinding

class AdoptionItemAdapter : Adapter<AdoptionItemAdapter.AdoptionItemViewHolder>() {
    private var nameList = ArrayList<String>()
    private var detailList = ArrayList<String?>()
    fun setNameList(nameList: ArrayList<String>) {
        this.nameList = nameList
    }
    fun setDetailList(detailList: ArrayList<String?>) {
        this.detailList = detailList
    }

    class AdoptionItemViewHolder(val binding: AdoptionDetailItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptionItemViewHolder {
        return AdoptionItemViewHolder(
            AdoptionDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return nameList.size
    }

    override fun onBindViewHolder(holder: AdoptionItemViewHolder, position: Int) {
        holder.binding.itemName.text = nameList[position]
        holder.binding.itemNameDetail.text = detailList[position]
    }
}