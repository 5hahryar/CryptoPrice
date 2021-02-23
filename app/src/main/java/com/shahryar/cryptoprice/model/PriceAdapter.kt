package com.shahryar.cryptoprice.model

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.databinding.ItemPriceBinding
import com.shahryar.cryptoprice.repository.PriceDataDiffCallback

class PriceAdapter(private val context: Context): ListAdapter<DataX, PriceAdapter.ViewHolder>(PriceDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val priceDifference = getItem(position).quote.USD.percent_change_24h.toString()
        holder.binding.name.text = getItem(position).name
        holder.binding.symbol.text = getItem(position).symbol
        if (priceDifference.toDouble() > 0 ) holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.green))
        else holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.red))
        holder.binding.priceDifference.text = "%$priceDifference"
        holder.binding.price.text = "$${getItem(position).quote.USD.price}"
    }

    class ViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)
}