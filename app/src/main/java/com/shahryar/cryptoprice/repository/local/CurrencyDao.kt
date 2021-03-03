package com.shahryar.cryptoprice.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahryar.cryptoprice.model.Currency

@Dao
interface CurrencyDao {

    @Query("select * from currencies")
    fun getCurrencies(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)
}