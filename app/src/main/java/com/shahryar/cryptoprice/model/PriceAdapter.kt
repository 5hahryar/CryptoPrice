package com.shahryar.cryptoprice.model

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.databinding.ItemHeaderBinding
import com.shahryar.cryptoprice.databinding.ItemPriceBinding
import com.shahryar.cryptoprice.repository.PriceDataDiffCallback

class PriceAdapter: ListAdapter<DataX, RecyclerView.ViewHolder>(PriceDataDiffCallback()) {

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context

        val headerBinding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val itemBinding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == ITEM_VIEW_TYPE_HEADER) HeaderViewHolder(headerBinding) else ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val priceDifference = getItem(position).quote.USD.percent_change_24h.toString()
                holder.binding.name.text = getItem(position).name
                holder.binding.symbol.text = getItem(position).symbol
                if (priceDifference.toDouble() > 0 ) holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.priceDifference.text = "$priceDifference%"
                holder.binding.price.text = "$${getItem(position).quote.USD.price}"
            }
            is HeaderViewHolder -> {
                val priceDifference = getItem(position).quote.USD.percent_change_24h.toString()
                val d7Difference = getItem(position).quote.USD.percent_change_7d.toString()
                val h24Difference = getItem(position).quote.USD.percent_change_24h.toString()
                holder.binding.name.text = getItem(position).name
                holder.binding.symbol.text = getItem(position).symbol
                if (priceDifference.toDouble() > 0 ) holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.priceDifference.text = "$priceDifference%"
                holder.binding.price.text = "$${getItem(position).quote.USD.price}"
                holder.binding.marketCap.text = getItem(position).quote.USD.market_cap.toString()
                if (d7Difference.toDouble() > 0 ) holder.binding.d7.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.d7.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.d7.text = "$d7Difference%"
                if (h24Difference.toDouble() > 0 ) holder.binding.h24.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.h24.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.h24.text = "$h24Difference%"
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < 2) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    class ViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

    class HeaderViewHolder(val binding: ItemHeaderBinding): RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

}