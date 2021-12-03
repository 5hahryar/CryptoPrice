package com.shahryar.cryptoprice.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shahryar.cryptoprice.data.model.Currency

@Dao
interface CurrencyDao {

    @Query("select * from currencies")
    suspend fun getCurrencies(): List<Currency>

    @Query("SELECT * FROM currencies ORDER BY name")
    fun getCurrenciesByName(): LiveData<List<Currency>>

    @Query("SELECT * FROM currencies ORDER BY price")
    fun getCurrenciesByPrice(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)

    @Query("SELECT * FROM currencies WHERE name = 'bitcoin'")
    fun getCoin(): Currency?
}