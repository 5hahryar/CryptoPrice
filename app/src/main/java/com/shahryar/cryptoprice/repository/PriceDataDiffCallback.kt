package com.shahryar.cryptoprice.repository

import androidx.recyclerview.widget.DiffUtil
import com.shahryar.cryptoprice.model.String

class PriceDataDiffCallback: DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}