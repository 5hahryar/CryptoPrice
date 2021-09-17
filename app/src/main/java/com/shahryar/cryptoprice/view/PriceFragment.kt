package com.shahryar.cryptoprice.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
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
        return ComposeView(requireContext()).apply {
            setContent {
                PriceFragmentView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupRecyclerView()
//        setListeners()
//        subscribeViews()
    }

    @Composable
    fun PriceFragmentView() {
        Column {
            PriceTopAppBar()
            PriceContent(mViewModel.currencies.observeAsState().value)
        }
    }

    @Composable
    private fun PriceContent(currencies: List<Currency>?) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = mViewModel.isRefreshing.collectAsState().value),
            onRefresh = { mViewModel.refreshData() }) {
            if (!currencies.isNullOrEmpty()) {
                LazyColumn {
                    items(currencies) { item ->
                        PriceItemView(item = item)
                    }
                }
            }
        }
    }

    @Composable
    fun PriceItemView(item: Currency) {
        Card(
            Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(15.dp, 8.dp)
                .clickable(
                    indication = rememberRipple(
                        bounded = true,
                        color = colorResource(id = R.color.ripple)
                    ),
                    onClick = {
                        CurrencyBottomSheetFragment(item).show(
                            parentFragmentManager,
                            "TAG"
                        )
                    },
                    enabled = true,
                    interactionSource = MutableInteractionSource()
                ),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = colorResource(id = R.color.item_price_card),
            elevation = 0.dp
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
//                                    .background(color = Color.Red)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Column(verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)) {
                                Text(
                                    text = item.symbol,
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1
                                )
                                Text(
                                    text = item.name,
                                    color = colorResource(id = R.color.text_color),
                                    fontSize = 20.sp,
                                    maxLines = 1
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
//                                            .background(color = Color.Cyan),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${
                                        String.format(
                                            "%.2f",
                                            item.percent_change_24h
                                        )
                                    }%",
                                    color = if (item.percent_change_24h >= 0) colorResource(
                                        id = R.color.green
                                    ) else colorResource(
                                        id = R.color.red
                                    ),
                                    fontSize = 14.sp,
                                    maxLines = 1,
//                                                modifier = Modifier.background(Color.Yellow)
                                )
                                Text(
                                    text = "$${item.price}",
                                    color = colorResource(id = R.color.text_color),
                                    fontSize = 20.sp,
                                    maxLines = 1,
//                                            modifier = Modifier.background(Color.Red)
                                )
                            }
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                )
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Column(verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)) {
                                Text(
                                    text = "Market cap",
                                    color = colorResource(id = R.color.text_alpha),
                                    fontSize = 14.sp,
                                    maxLines = 1
                                )
                                Text(
                                    text = "$${item.market_cap}",
                                    color = colorResource(id = R.color.text_color),
                                    fontSize = 19.sp,
                                    maxLines = 1
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
//                                            .background(color = Color.Cyan)
                        ) {
                            Row {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "30 Day",
                                        color = colorResource(id = R.color.text_alpha),
                                        fontSize = 14.sp,
                                        maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                    )
                                    Text(
                                        text = "${
                                            String.format(
                                                "%.2f",
                                                item.percent_change_30d
                                            )
                                        }%",
                                        color = if (item.percent_change_30d >= 0) colorResource(
                                            id = R.color.green
                                        ) else colorResource(
                                            id = R.color.red
                                        ),
                                        fontSize = 19.sp,
                                        maxLines = 1,
//                                                    modifier = Modifier.background(Color.Red)
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "7 Day",
                                        color = colorResource(id = R.color.text_alpha),
                                        fontSize = 14.sp,
                                        maxLines = 1,
//                                                    modifier = Modifier.background(Color.Yellow)
                                    )
                                    Text(
                                        text = "${
                                            String.format(
                                                "%.2f",
                                                item.percent_change_7d
                                            )
                                        }%",
                                        color = if (item.percent_change_7d >= 0) colorResource(
                                            id = R.color.green
                                        ) else colorResource(
                                            id = R.color.red
                                        ),
                                        fontSize = 19.sp,
                                        maxLines = 1,
//                                                    modifier = Modifier.background(Color.Red)
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun PriceTopAppBar() {
        TopAppBar(
            title = { Text(text = getString(R.string.app_name)) },
            backgroundColor = colorResource(id = R.color.primary),
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu")
                }
            }
        )
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

//        mViewModel.isRefreshing.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                refreshLayout.isRefreshing = mViewModel.isRefreshing.get()!!
//            }
//        })
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
                CurrencyBottomSheetFragment(item).show(
                    requireActivity().supportFragmentManager,
                    "CurrencyBottomSheet"
                )
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

    @Composable
    @Preview
    fun Preview() {
        PriceFragmentView()
    }
}