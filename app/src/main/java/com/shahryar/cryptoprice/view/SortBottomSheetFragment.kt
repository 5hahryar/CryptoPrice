package com.shahryar.cryptoprice.view

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SortBottomSheetFragment private constructor(private val listener: OnSortItemSelectedListener) :
    BottomSheetDialogFragment() {

    companion object {
        fun newInstance(listener: OnSortItemSelectedListener): SortBottomSheetFragment =
            SortBottomSheetFragment(listener)
    }

    private val sortItems = listOf(
        Pair("market_cap", "Market Cap"),
        Pair("name", "Name"),
        Pair("price", "Price")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { SortSheetCompose() }
        }
    }

    @Composable
    fun SortSheetCompose() {
        val onItemClick: (String) -> Unit = {
            listener.onSortItemSelected(it)
            dismiss()
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text(modifier = Modifier.padding(bottom = 10.dp), text = "Sort by", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            sortItems.forEach {
                SortItem(it, onItemClick)
            }
        }
    }

    @Composable
    fun SortItem(item: Pair<String, String>, onItemClick: (String) -> Unit) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onItemClick(item.first) },
            verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = item.second,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp
            )
        }
    }

    interface OnSortItemSelectedListener {
        fun onSortItemSelected(key: String)
    }
}