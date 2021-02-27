package com.shahryar.cryptoprice.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.application.DEFAULT_HEADER_SIZE
import com.shahryar.cryptoprice.databinding.ItemHeaderBinding
import com.shahryar.cryptoprice.databinding.ItemPriceBinding
import com.shahryar.cryptoprice.repository.PriceDataDiffCallback

class PriceAdapter: ListAdapter<DataX, RecyclerView.ViewHolder>(PriceDataDiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

    private lateinit var context: Context
    var headerSize: Int = DEFAULT_HEADER_SIZE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context

        //Create binding object for header item (extended card)
        val headerBinding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //Create binding object for normal item
        val itemBinding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return if (viewType == ITEM_VIEW_TYPE_HEADER) HeaderViewHolder(headerBinding) else ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            //Bind views for normal item view
            is ViewHolder -> {
                val priceDifference = getItem(position).quote.USD.percent_change_24h.toString()
                holder.binding.name.text = getItem(position).name
                holder.binding.symbol.text = getItem(position).symbol
                if (priceDifference.toDouble() > 0 ) holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.priceDifference.text = "$priceDifference%"
                holder.binding.price.text = "$${getItem(position).quote.USD.price}"
            }
            //Bind views for header item view
            is HeaderViewHolder -> {
                val priceDifference = getItem(position).quote.USD.percent_change_24h.toString()
                val d7Difference = getItem(position).quote.USD.percent_change_7d.toString()
                val d30Difference = getItem(position).quote.USD.percent_change_30d.toString()
                holder.binding.name.text = getItem(position).name
                holder.binding.symbol.text = getItem(position).symbol
                if (priceDifference.toDouble() > 0 ) holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.priceDifference.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.priceDifference.text = "$priceDifference%"
                holder.binding.price.text = "$${getItem(position).quote.USD.price}"
                holder.binding.marketCap.text = "$${getItem(position).quote.USD.market_cap}"
                if (d7Difference.toDouble() > 0 ) holder.binding.d7.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.d7.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.d7.text = "$d7Difference%"
                if (d30Difference.toDouble() > 0 ) holder.binding.d30.setTextColor(context.resources.getColor(R.color.green))
                else holder.binding.d30.setTextColor(context.resources.getColor(R.color.red))
                holder.binding.d30.text = "$d30Difference%"
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        //Return view type as header, if position is below header size
        return if (position < this.headerSize) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    //ViewHolder for a normal card item
    class ViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

    //ViewHolder for a header item (extended card)
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