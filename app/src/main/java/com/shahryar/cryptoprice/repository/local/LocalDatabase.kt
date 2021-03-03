package com.shahryar.cryptoprice.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shahryar.cryptoprice.model.Currency

@Database(entities = [Currency::class], version = 1)
abstract class LocalDatabase: RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}

    private lateinit var INSTANCE: LocalDatabase

    fun getDatabase(context: Context): LocalDatabase {
        synchronized(LocalDatabase::class) {
            if (!::INSTANCE.isInitialized){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    LocalDatabase::class.java,
                    "localDatabase")
                    .build()
            }
        }
        return INSTANCE
    }
