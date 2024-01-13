package com.leo.adoption.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.leo.adoption.R
import com.leo.adoption.databinding.ActivityMainBinding
import com.leo.adoption.db.AdoptionDatabase
import com.leo.adoption.viewmodel.HomeViewModel
import com.leo.adoption.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: HomeViewModel by lazy {
        val mealDatabase = AdoptionDatabase.getInstance(this)
        val homeViewModelProvider = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProvider)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation
        val navController = findNavController(R.id.frag_host)
        bottomNavigationView.setupWithNavController(navController)
    }
}