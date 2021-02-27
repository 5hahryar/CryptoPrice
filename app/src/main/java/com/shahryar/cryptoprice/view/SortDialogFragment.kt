package com.shahryar.cryptoprice.view

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.shahryar.cryptoprice.R
import kotlinx.android.synthetic.main.fragment_sort_dialog_list_dialog.*

class SortDialogFragment(private val listener: OnSortItemSelectedListener) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sort_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = ItemAdapter()
    }

    private inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_sort_dialog_list_dialog_item, parent, false)) {

        val text: TextView = itemView.findViewById(R.id.text)
    }

    private inner class ItemAdapter : RecyclerView.Adapter<ViewHolder>() {

        val list = listOf(Pair("market_cap", "Default: Market Cap"),
                Pair("name", "Name"),
                Pair("price", "Price"))

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = list[position].second
            holder.itemView.setOnClickListener {
                listener.onSortItemSelected(list[position].first)
                dismiss()
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    companion object {
        // TODO: Customize parameters
        fun newInstance(listener: OnSortItemSelectedListener): SortDialogFragment = SortDialogFragment(listener)
    }

    interface OnSortItemSelectedListener {
        fun onSortItemSelected(key: String)
    }
}