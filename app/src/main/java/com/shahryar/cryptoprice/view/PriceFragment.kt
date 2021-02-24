package com.shahryar.cryptoprice.view

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.databinding.FragmentPriceBinding
import com.shahryar.cryptoprice.model.PriceAdapter
import com.shahryar.cryptoprice.viewModel.PriceViewModel
import kotlinx.android.synthetic.main.fragment_price.*
import kotlinx.android.synthetic.main.fragment_price.view.*

class PriceFragment : Fragment() {

    private lateinit var binding: FragmentPriceBinding
    private lateinit var viewModel: PriceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_price, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        this.viewModel = ViewModelProvider(this).get(PriceViewModel::class.java)
        binding.viewModel = this.viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position < 2) 2 else 1
            }
        }
        binding.recyclerView.layoutManager = layoutManager
        val adapter = PriceAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.getData()

        viewModel.response.observe(viewLifecycleOwner, {
            adapter.submitList(it.data)
            binding.refreshLayout.isRefreshing = false
        })

        recyclerView.setOnScrollChangeListener { view, i, i2, i3, i4 ->
            if (!view.canScrollVertically(-1)) appBarLayout.elevation = 0f else appBarLayout.elevation = 15f
        }

        refreshLayout.setOnRefreshListener { viewModel.getData() }
    }
}