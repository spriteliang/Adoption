package com.leo.adoption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.adoption.R
import com.leo.adoption.databinding.RowItemViewpagerBinding

class OnboardingAdapter : RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {
    var testList = arrayListOf(
        "https://www.pet.gov.tw/upload/pic/1703052945790.png",
        "https://www.pet.gov.tw/upload/pic/1703052945790.png",
        "https://www.pet.gov.tw/upload/pic/1703052945790.png"
    )

    class ViewHolder(val binding: RowItemViewpagerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowItemViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(testList[position]).into(holder.binding.imgIntro)
    }

}