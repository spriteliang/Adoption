package com.leo.adoption.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AdoptionApi by lazy {
        Retrofit.Builder()
            //API網址
            .baseUrl("https://data.moa.gov.tw/Service/OpenData/")
            //使用 Gson 解析回應內容
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdoptionApi::class.java)
    }
}