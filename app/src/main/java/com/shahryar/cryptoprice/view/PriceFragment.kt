package com.shahryar.cryptoprice.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.core.DEFAULT_HEADER_SIZE
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.databinding.FragmentPriceBinding
import com.shahryar.cryptoprice.view.adapter.PriceAdapter
import com.shahryar.cryptoprice.viewModel.PriceViewModel
import kotlinx.android.synthetic.main.empty_list_layout.*
import kotlinx.android.synthetic.main.fragment_price.*
import kotlinx.android.synthetic.main.no_api_warning.*
import org.koin.android.ext.android.inject

class PriceFragment : Fragment() {

    private lateinit var mBinding: FragmentPriceBinding
    private val mViewModel: PriceViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentPriceBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setListeners()
        subscribeViews()
    }

    private fun subscribeViews() {
        mViewModel.currencies.observe(viewLifecycleOwner, {
            refreshLayout.isRefreshing = false
            if (it != null) {

                (recyclerView.adapter as PriceAdapter).submitList(it)
            }
        })
    }

    private fun setListeners() {
        //Change appBar elevation on recyclerView scroll
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener { view, _, _, _, _ ->
                if (!view.canScrollVertically(-1)) appBarLayout.elevation =
                    0f else appBarLayout.elevation = 15f
            }
        }

        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            mViewModel.refreshData()
        }

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.layout -> {
                    changeRecyclerViewLayout()
                    swapLayoutMenuItem(it)
                    true
                }
//                R.id.sort -> {
//                    SortDialogFragment.newInstance(this)
//                        .show(requireActivity().supportFragmentManager, "Sort By")
//                    true
//                }
                R.id.settings -> {
                    findNavController().navigate(R.id.action_priceFragment_to_settingsFragment)
                    true
                }
                R.id.help -> {
                    AboutDialog().show(requireActivity().supportFragmentManager, "About Dialog")
                    true
                }
                else -> false
            }
        }

        enterKeyButton.setOnClickListener { findNavController().navigate(R.id.action_priceFragment_to_settingsFragment) }

        getKeyButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://coinmarketcap.com/api/pricing/")
                )
            )
        }

        refreshButton.setOnClickListener {
            mViewModel.refreshData()
            refreshLayout.isRefreshing = true
        }

        mViewModel.isRefreshing.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                refreshLayout.isRefreshing = mViewModel.isRefreshing.get()!!
            }
        })
    }

    //Change title and icon of layout menu item
    private fun swapLayoutMenuItem(menuItem: MenuItem) {
        menuItem.icon = if (menuItem.title.equals("Grid Layout"))
            resources.getDrawable(R.drawable.ic_view_list_24px) else
            resources.getDrawable(R.drawable.ic_grid_view_24px)
        menuItem.title =
            if (menuItem.title.equals("Grid Layout")) "Linear Layout" else "Grid Layout"
    }

    //Setup recyclerView with needed parameters
    private fun setupRecyclerView() {
        val layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position < DEFAULT_HEADER_SIZE) DEFAULT_HEADER_SIZE else 1
            }
        }
        mBinding.recyclerView.layoutManager = layoutManager
        val adapter = PriceAdapter()
        adapter.setOnItemClickedListener(object : PriceAdapter.OnItemClickedListener {
            override fun onItemClicked(item: Currency) {
                CurrencyOverviewBottomSheet(item).show(requireActivity().supportFragmentManager, "CurrencyBottomSheet")
            }
        })
        mBinding.recyclerView.adapter = adapter
    }

    //Swap recyclerView layout manager between list and grid view
    private fun changeRecyclerViewLayout() {
        if (recyclerView.layoutManager is GridLayoutManager) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            (recyclerView.adapter as PriceAdapter).headerSize =
                (recyclerView.adapter as PriceAdapter).currentList.size
        } else {
            val layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position < 2) 2 else 1
                }
            }
            recyclerView.layoutManager = layoutManager
            (recyclerView.adapter as PriceAdapter).headerSize = DEFAULT_HEADER_SIZE
        }
    }
}