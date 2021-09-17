package com.shahryar.cryptoprice.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.core.DEFAULT_HEADER_SIZE
import com.shahryar.cryptoprice.databinding.ItemHeaderBinding
import com.shahryar.cryptoprice.databinding.ItemPriceBinding
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.viewModel.PriceViewModel

class PriceAdapter: ListAdapter<Currency, RecyclerView.ViewHolder>(PriceDataDiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

    private lateinit var context: Context
    private var onItemClickedListener: OnItemClickedListener? = null
    var headerSize: Int = DEFAULT_HEADER_SIZE
    lateinit var viewModel: PriceViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context

        //Create binding object for header item (extended card)
        val headerBinding = ItemHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        //Create binding object for normal item
        val itemBinding = ItemPriceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return if (viewType == ITEM_VIEW_TYPE_HEADER) HeaderViewHolder(headerBinding) else ViewHolder(
            itemBinding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            //Bind views for normal item view
            is ViewHolder -> {
                holder.bind(getItem(position))
                holder.binding.priceCard.setOnClickListener {
                    onItemClickedListener?.onItemClicked(getItem(position))
                }
            }
            //Bind views for header item view
            is HeaderViewHolder -> {
                holder.bind(getItem(position))
                holder.binding.priceCard.setOnClickListener {
                    onItemClickedListener?.onItemClicked(getItem(position))
                }
            }
        }

    }

    fun filterList(query: String, latestList: List<Currency>) {
        val filteredList = latestList.filter {
            it.name.toLowerCase().contains(query.toLowerCase())
        }
        submitList(filteredList)
    }

    override fun getItemViewType(position: Int): Int {
        //Return view type as header, if position is below header size
        return if (position < this.headerSize) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    fun setOnItemClickedListener(listener: OnItemClickedListener) {
        this.onItemClickedListener = listener
    }

    //ViewHolder for a normal card item
    class ViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Currency) {
            binding.priceData = item
        }
    }

    //ViewHolder for a header item (extended card)
    class HeaderViewHolder(val binding: ItemHeaderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Currency) {
            binding.data = item
        }
    }

    interface OnItemClickedListener {
        fun onItemClicked(item: Currency)
    }

}