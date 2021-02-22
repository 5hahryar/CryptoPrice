package com.shahryar.cryptoprice.repository

import androidx.recyclerview.widget.DiffUtil
import com.shahryar.cryptoprice.model.DataX

class PriceDataDiffCallback: DiffUtil.ItemCallback<DataX>() {

    override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
        return oldItem == newItem
    }
}