package com.leo.adoption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.leo.adoption.databinding.AdoptionItemBinding
import com.leo.adoption.pojo.Adoption

class AdoptionAdapter : Adapter<AdoptionAdapter.AdoptionViewHolder>() {
    private var adoptionsList = ArrayList<Adoption>()
    fun setAdoptions(adoptionsList: ArrayList<Adoption>) {
        this.adoptionsList = adoptionsList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdoptionViewHolder {
        return AdoptionViewHolder(
            AdoptionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return adoptionsList.size
    }

    override fun onBindViewHolder(holder: AdoptionViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(adoptionsList[position].album_file)
            .into(holder.binding.adoptionImage)

        holder.binding.apply {
            textVariety.text = adoptionsList[position].animal_Variety
            textKind.text = adoptionsList[position].animal_kind
            when (adoptionsList[position].animal_sex) {
                "M" -> textSex.text = "公"
                "F" -> textSex.text = "母"
            }
            textAddress.text = adoptionsList[position].animal_place
        }
    }

    class AdoptionViewHolder(val binding: AdoptionItemBinding) : ViewHolder(binding.root)
}
