package com.leo.adoption.retrofit

import com.leo.adoption.pojo.Adoption
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AdoptionApi {


    //    https://data.moa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL&IsTransData=1
    @GET("TransService.aspx")
    fun getAdoptions(
        @Query("UnitId") UnitId: String, @Query("IsTransData") IsTransData: Int
    ): Call<List<Adoption>>


}