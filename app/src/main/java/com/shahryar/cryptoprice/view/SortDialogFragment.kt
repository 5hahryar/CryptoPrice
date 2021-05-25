package com.shahryar.cryptoprice.view

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahryar.cryptoprice.R
import kotlinx.android.synthetic.main.fragment_sort_dialog_list_dialog.*

class SortDialogFragment private constructor(private val listener: OnSortItemSelectedListener) :
    BottomSheetDialogFragment() {

    companion object {
        fun newInstance(listener: OnSortItemSelectedListener): SortDialogFragment =
            SortDialogFragment(listener)
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
            setContent { SortDialogCompose() }
        }
    }

    @Composable
    fun SortDialogCompose() {
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