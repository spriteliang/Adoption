package com.leo.adoption.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leo.adoption.pojo.Adoption

@Database(entities = [Adoption::class], version = 1)
//@TypeConverters(AdoptionTypeConvertor::class)
abstract class AdoptionDatabase : RoomDatabase() {
    abstract fun adoptionDao(): AdoptionDao

    companion object {
        @Volatile
        var INSTANCE: AdoptionDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AdoptionDatabase {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, AdoptionDatabase::class.java, "adoption.db")
                        .fallbackToDestructiveMigration().build()
            }
            return INSTANCE as AdoptionDatabase
        }
    }
}