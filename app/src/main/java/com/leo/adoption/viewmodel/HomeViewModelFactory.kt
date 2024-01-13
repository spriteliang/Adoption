package com.leo.adoption.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leo.adoption.db.AdoptionDatabase

class HomeViewModelFactory(
    private val adoptionDatabase: AdoptionDatabase
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(adoptionDatabase) as T
    }
}