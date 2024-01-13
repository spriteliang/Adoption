package com.leo.adoption.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leo.adoption.db.AdoptionDatabase
import com.leo.adoption.pojo.Adoption
import kotlinx.coroutines.launch

class AdoptionViewModel(val adoptionDatabase: AdoptionDatabase) : ViewModel() {

    fun insertAdoption(adoption: Adoption) {
        viewModelScope.launch {
            adoptionDatabase.adoptionDao().upsert(adoption)
        }
    }


}