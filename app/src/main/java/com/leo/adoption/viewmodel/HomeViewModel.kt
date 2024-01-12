package com.leo.adoption.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leo.adoption.pojo.Adoption
import com.leo.adoption.pojo.AdoptionList
import com.leo.adoption.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val TAG: String? = HomeViewModel::class.java.simpleName
    private val adoptionLiveData = MutableLiveData<List<Adoption>>()


    fun getAdoption() {
        RetrofitInstance.api.getAdoptions("QcbUEzN6E6DL", 1)
            .enqueue(object : Callback<List<Adoption>> {
                override fun onResponse(
                    call: Call<List<Adoption>>,
                    response: Response<List<Adoption>>
                ) {
                    if (response.body() != null) {
                        adoptionLiveData.value = response.body()
                        Log.d(TAG, "onResponse HomeViewModel ${response.body()}")
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
}