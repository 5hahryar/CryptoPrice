package com.shahryar.cryptoprice.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.databinding.ActivityWidgetConfigureBinding
import com.shahryar.cryptoprice.databinding.ItemConfigureWidgetBinding
import com.shahryar.cryptoprice.model.Currency

class WidgetRecyclerViewAdapter :
    ListAdapter<Currency, RecyclerView.ViewHolder>(PriceDataDiffCallback()) {

    private lateinit var clickListener: OnItemClickListener

    fun setOnItemClickedListener(listener: OnItemClickListener){
        clickListener = listener
    }

    class ViewHolder(
        private val binding: ItemConfigureWidgetBinding,
        private val listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Currency) {
            binding.data = item
            binding.clickListener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemConfigureWidgetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), clickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClicked(item: Currency)
    }
}
