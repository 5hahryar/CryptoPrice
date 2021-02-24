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

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        val adapter = PriceAdapter(requireContext())
        binding.recyclerView.adapter = adapter
        viewModel.getData()

        viewModel.response.observe(viewLifecycleOwner, {
            adapter.submitList(it.data)
//            binding.refreshLayout.isRefreshing = false
        })

//        binding.refreshLayout.setOnRefreshListener { viewModel.getData() }
    }
}