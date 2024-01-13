package com.leo.adoption.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leo.adoption.pojo.Adoption

@Dao
interface AdoptionDao {

    //預設萬一執行出錯怎麼辦，REPLACE為覆蓋
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(adoption: Adoption)

    @Delete
    suspend fun delete(adoption: Adoption)

    @Query("SELECT * FROM adoptionInformation")
    fun getAllAdoption(): LiveData<List<Adoption>>
}