package com.shahryar.cryptoprice.model

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.shahryar.cryptoprice.R

@BindingAdapter("priceDifference")
fun TextView.setPriceDifference(price: Double) {
    if (price > 0) setTextColor(resources.getColor(R.color.green))
    else setTextColor(resources.getColor(R.color.red))
    text = "$price%"
}