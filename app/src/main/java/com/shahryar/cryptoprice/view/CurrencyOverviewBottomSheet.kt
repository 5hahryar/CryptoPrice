package com.shahryar.cryptoprice.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.databinding.BottomSheetCurrencyOverviewBinding
import com.shahryar.cryptoprice.model.Currency

class CurrencyOverviewBottomSheet (private val currency: Currency) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: BottomSheetCurrencyOverviewBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_currency_overview, container, false)
        binding.currency = this.currency
        return binding.root
    }
}