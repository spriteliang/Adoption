package com.leo.adoption.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leo.adoption.db.AdoptionDatabase

class AdoptionViewModelFactory(
    private val adoptionDatabase: AdoptionDatabase
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AdoptionViewModel(adoptionDatabase) as T
    }
}