package com.shahryar.cryptoprice.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shahryar.cryptoprice.data.model.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("select * from currencies")
    fun getCurrencies(): Flow<List<Currency>>

    @Query("SELECT * FROM currencies ORDER BY name")
    fun getCurrenciesByName(): LiveData<List<Currency>>

    @Query("SELECT * FROM currencies ORDER BY price")
    fun getCurrenciesByPrice(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)

    @Query("SELECT * FROM currencies WHERE name = 'bitcoin'")
    fun getCoin(): Currency?
}