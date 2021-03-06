package com.shahryar.cryptoprice.repository.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.shahryar.cryptoprice.model.Currency

@Dao
interface CurrencyDao {

    @Query("select * from currencies")
    fun getCurrencies(): LiveData<List<Currency>>

    @Query("SELECT * FROM currencies ORDER BY name")
    fun getCurrenciesByName(): LiveData<List<Currency>>

    @Query("SELECT * FROM currencies ORDER BY price")
    fun getCurrenciesByPrice(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<Currency>)
}