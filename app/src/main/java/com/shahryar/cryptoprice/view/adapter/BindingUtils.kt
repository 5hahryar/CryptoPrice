package com.shahryar.cryptoprice.view.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shahryar.cryptoprice.R

@BindingAdapter("priceDifference")
fun TextView.setPriceDifference(price: Double) {
    text = if (price > 0) {
        setTextColor(resources.getColor(R.color.green))
        "+${String.format("%.2f", price)}%"
    }
    else {
        setTextColor(resources.getColor(R.color.red))
        "${String.format("%.2f", price)}%"
    }
}