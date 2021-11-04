package com.shahryar.cryptoprice

import androidx.lifecycle.LiveData
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.data.repository.Repository

class FakeRepository: Repository {
    override fun getCurrencies(): LiveData<List<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrencies(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh() {
        TODO("Not yet implemented")
    }
}