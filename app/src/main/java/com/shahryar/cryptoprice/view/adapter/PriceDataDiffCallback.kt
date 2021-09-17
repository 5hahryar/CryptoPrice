package com.shahryar.cryptoprice.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shahryar.cryptoprice.data.model.Currency

class PriceDataDiffCallback: DiffUtil.ItemCallback<Currency>() {

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }
}