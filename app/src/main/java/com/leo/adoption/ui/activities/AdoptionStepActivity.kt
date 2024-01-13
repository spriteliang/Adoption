package com.leo.adoption.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.leo.adoption.R
import com.leo.adoption.databinding.ActivityAdoptionBinding
import com.leo.adoption.databinding.ActivityAdoptionStepBinding

class AdoptionStepActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdoptionStepBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptionStepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(R.drawable.img_step).into(binding.imgStep)

    }
}