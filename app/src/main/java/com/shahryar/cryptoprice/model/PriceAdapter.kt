package com.shahryar.cryptoprice.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shahryar.cryptoprice.R

class PriceAdapter(val context: Context, var data: Data?): RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_price, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(data?.data?.get(position)?.name)
        holder.price.setText(data?.data?.get(position)?.quote?.USD?.price.toString())
    }

    override fun getItemCount(): Int {
        return if (data != null) data!!.data.size
        else 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.findViewById<TextView>(R.id.name)
        var price = itemView.findViewById<TextView>(R.id.price)
    }
}