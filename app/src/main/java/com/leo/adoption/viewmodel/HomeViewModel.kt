package com.leo.adoption.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leo.adoption.db.AdoptionDatabase
import com.leo.adoption.pojo.Adoption
import com.leo.adoption.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private var adoptionDatabase: AdoptionDatabase) : ViewModel(
) {
    private val TAG: String? = HomeViewModel::class.java.simpleName
    private val adoptionLiveData = MutableLiveData<List<Adoption>>()
    private val favoriteLiveData = adoptionDatabase.adoptionDao().getAllAdoption()
    private val searchedMealsLiveData = MutableLiveData<List<Adoption>>()


    fun getAdoption() {
        RetrofitInstance.api.getAdoptions("QcbUEzN6E6DL", 1)
            .enqueue(object : Callback<List<Adoption>> {
                override fun onResponse(
                    call: Call<List<Adoption>>,
                    response: Response<List<Adoption>>
                ) {
                    if (response.body() != null) {
                        adoptionLiveData.value = response.body()
//                        Log.d(TAG, "onResponse HomeViewModel ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<List<Adoption>>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun observeAdoptionItemLivedata(): LiveData<List<Adoption>> {
        return adoptionLiveData
    }

    fun observeFavoriteLivedata(): LiveData<List<Adoption>> {
        return favoriteLiveData
    }

    fun observeSearchedMealsLiveData(): LiveData<List<Adoption>> = searchedMealsLiveData
    fun deleteMeal(adoption: Adoption) {
        viewModelScope.launch {
            adoptionDatabase.adoptionDao().delete(adoption)
        }
    }

    fun insertMeal(adoption: Adoption) {
        viewModelScope.launch {
            adoptionDatabase.adoptionDao().upsert(adoption)
        }
    }

    fun searchAdoption(searchQuery: String) =
        RetrofitInstance.api.getAdoptions("QcbUEzN6E6DL", 1).enqueue(
            object : Callback<List<Adoption>> {
                override fun onResponse(
                    call: Call<List<Adoption>>,
                    response: Response<List<Adoption>>
                ) {
                    val adoptionList = response.body() as ArrayList<Adoption>
                    if (searchQuery != null) {
                        var filterList = ArrayList<Adoption>()
                        for (i in adoptionList!!) {
                            if (i.animal_kind!!.contains(searchQuery) || i.shelter_address!!.contains(searchQuery)
                            ) {
                                filterList.add(i)
                            }
                        }
                        filterList.let {
                            searchedMealsLiveData.postValue(it)
                        }
                    }
                }

                override fun onFailure(call: Call<List<Adoption>>, t: Throwable) {
                    Log.e("HomeViewModel", t.message.toString())
                }
            }
        )
}