package com.shahryar.cryptoprice.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.application.DEFAULT_HEADER_SIZE
import com.shahryar.cryptoprice.model.Currency
import com.shahryar.cryptoprice.model.adapter.PriceAdapter
import com.shahryar.cryptoprice.viewModel.PriceViewModel
import com.shahryar.cryptoprice.viewModel.PriceViewModelFactory

class PriceFragment : Fragment(), SortBottomSheetFragment.OnSortItemSelectedListener {

    private lateinit var viewModel: PriceViewModel
//    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.viewModel = ViewModelProvider(
            this,
            PriceViewModelFactory(requireContext())
        ).get(PriceViewModel::class.java)

        return ComposeView(requireContext()).apply {
            setContent {
                PriceCompose()
            }
        }
    }

    @Composable
    fun PriceCompose() {
        val isMenuExpanded = remember { mutableStateOf(false) }
        val list = viewModel.currencies.observeAsState()
        val isApiKeyAvailable = viewModel.isApiKeyAvailable.collectAsState()

        val onPriceItemClick: (Currency) -> Unit = {
            CurrencyBottomSheetFragment(it).show(requireActivity().supportFragmentManager, "CurrencySheet")
        }
        val onSearchClickListener: () -> Unit = {}
        val onSortClickListener: () -> Unit = {
            SortBottomSheetFragment.newInstance(object : SortBottomSheetFragment.OnSortItemSelectedListener {
                override fun onSortItemSelected(key: String) {
                    viewModel.onSort(key)
                }
            }).show(
                requireActivity().supportFragmentManager, "SortDialog"
            )
        }
        val onSettingsClickListener: () -> Unit = {
            findNavController().navigate(R.id.action_priceFragment_to_settingsFragment)
            isMenuExpanded.value = false
        }
        val onAboutClickListener: () -> Unit = {
            AboutDialog().show(
                requireActivity().supportFragmentManager,
                "AboutDialog"
            )
            isMenuExpanded.value = false
        }

        MaterialTheme {
            Column {
                Box {
                    Box(modifier = Modifier.absolutePadding(top = dimensionResource(id = R.dimen.actionBarSize))) {
                        if (isApiKeyAvailable.value) {
                            if (list.value.isNullOrEmpty()) {
                                Column(
                                    Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Something went wrong :(")
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Button(
                                        onClick = { viewModel.refreshData(requireContext()) },
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color.Black
                                        )
                                    ) {
                                        Text(text = "RETRY", color = Color.White)
                                    }
                                }
                            } else {
                                Body(list = list.value!!, onPriceItemClick)
                            }
                        } else {
                            NoApiBody()
                        }
                    }
                    AppBar(
                        isMenuExpanded,
                        onSearchClickListener,
                        onSortClickListener,
                        onSettingsClickListener,
                        onAboutClickListener
                    )
                }
            }
        }
    }

    @Composable
    fun NoApiBody() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Api key not found\nYou need an API key in order to use CryptoPrice",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Button(
                    onClick = {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://coinmarketcap.com/api/pricing/")
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black
                    )
                ) {
                    Text(text = "GET KEY", color = Color.White)
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    onClick = { findNavController().navigate(R.id.action_priceFragment_to_settingsFragment) }) {
                    Text(text = "ENTER KEY", color = Color.Black)
                }
            }
        }
    }

    @Composable
    fun Body(list: List<Currency>, onPriceItemClick: (Currency) -> Unit) {
        SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = viewModel.isRefreshing.collectAsState().value),
            onRefresh = { viewModel.refreshData(requireContext()) }
        ) {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(list) { item ->
                    PriceItem(item, onPriceItemClick)
                }
            }
        }
    }

    @Composable
    fun PriceItem(item: Currency, onPriceItemClick: (Currency) -> Unit) {
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
                    onClick = {onPriceItemClick(item)},
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
    fun AppBar(
        isMenuExpanded: MutableState<Boolean>,
        onSearchClickListener: () -> Unit,
        onSortClickListener: () -> Unit,
        onSettingsClickListener: () -> Unit,
        onAboutClickListener: () -> Unit
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "CryptoPrice",
                    color = colorResource(id = R.color.onPrimary),
                    fontWeight = FontWeight.Medium
                )
            },
            backgroundColor = colorResource(id = R.color.primary),
            actions = {
                Box {
                    Row {
//                        IconButton(onClick = onSearchClickListener) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_search_black_24dp),
//                                contentDescription = "Search",
//                                tint = colorResource(id = R.color.onPrimary)
//                            )
//                        }
                        IconButton(onClick = onSortClickListener) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_filter_list_24px),
                                contentDescription = "Sort",
                                tint = colorResource(id = R.color.onPrimary)
                            )
                        }
                        IconButton(onClick = {
                            isMenuExpanded.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Layout",
                                tint = colorResource(id = R.color.onPrimary)
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded.value,
                        onDismissRequest = { isMenuExpanded.value = false }) {
                        DropdownMenuItem(onClick = onSettingsClickListener) {
                            Text(text = "Settings")
                        }
                        DropdownMenuItem(onClick = onAboutClickListener) {
                            Text(text = "About")
                        }
                    }
                }
            }
        )
    }

    @Composable
    @Preview
    fun PricePreview() {
        val mockData = listOf(
            Currency(
                12,
                1.55555,
                1,
                "date_added",
                "last_updated",
                33333.4444,
                "Test Coin",
                "TST",
                10000.0,
                "2000.0",
                2.1,
                2.3,
                4.5,
                5.0,
                4.9,
                2341.341
            )
        )
//        PriceCompose()
//        Body(mockData)
        NoApiBody()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        searchView = (topAppBar.menu.findItem(R.id.search).actionView as SearchView).apply {
//
//        }

        setupRecyclerView()

        //Observe price data in order to update recyclerView
//        viewModel.currencies.observe(viewLifecycleOwner, {
//            (recyclerView.adapter as PriceAdapter).submitList(it)
//            viewModel.latestList = it
////            binding.refreshLayout.isRefreshing = false
//        })

        setStyles()

//        setListeners()

    }

    private fun setStyles() {
//        searchView.queryHint = "Search"
//        searchView.maxWidth = 650
    }

//    private fun setListeners() {
    //Change appBar elevation on recyclerView scroll
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            recyclerView.setOnScrollChangeListener { view, _, _, _, _ ->
//                if (!view.canScrollVertically(-1)) appBarLayout.elevation =
//                    0f else appBarLayout.elevation = 15f
//            }
//        }
//
//        refreshLayout.setOnRefreshListener { viewModel.refreshData(requireContext()) }
//
//        topAppBar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.layout -> {
//                    changeRecyclerViewLayout()
//                    swapLayoutMenuItem(it)
//                    true
//                }
//                R.id.sort -> {
//                    SortDialogFragment.newInstance(this)
//                        .show(requireActivity().supportFragmentManager, "Sort By")
//                    true
//                }
//                R.id.settings -> {
//                    findNavController().navigate(R.id.action_priceFragment_to_settingsFragment)
//                    true
//                }
//                R.id.help -> {
//                    AboutDialog().show(requireActivity().supportFragmentManager, "About Dialog")
//                    true
//                }
//                else -> false
//            }
//        }

//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                if (newText != null) {
////                    (recyclerView.adapter as PriceAdapter).filterList(newText, viewModel.latestList)
//                }
//                return true
//            }
//        })

//        enterKeyButton.setOnClickListener { findNavController().navigate(R.id.action_priceFragment_to_settingsFragment) }
//
//        getKeyButton.setOnClickListener {
//            startActivity(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://coinmarketcap.com/api/pricing/")
//                )
//            )
//        }
//
//        refreshButton.setOnClickListener {
//            viewModel.refreshData(requireContext())
////            refreshLayout.isRefreshing = true
//        }
//
//        viewModel.isRefreshing.addOnPropertyChangedCallback(object :
//            Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
////                refreshLayout.isRefreshing = viewModel.isRefreshing.get()!!
//            }
//        })
//    }

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
//        binding.recyclerView.layoutManager = layoutManager
        val adapter = PriceAdapter()
//        binding.recyclerView.adapter = adapter
    }

    //Swap recyclerView layout manager between list and grid view
//    private fun changeRecyclerViewLayout() {
//        if (recyclerView.layoutManager is GridLayoutManager) {
//            recyclerView.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//            (recyclerView.adapter as PriceAdapter).headerSize =
//                (recyclerView.adapter as PriceAdapter).currentList.size
//        } else {
//            val layoutManager =
//                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
//            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    return if (position < 2) 2 else 1
//                }
//            }
//            recyclerView.layoutManager = layoutManager
//            (recyclerView.adapter as PriceAdapter).headerSize = DEFAULT_HEADER_SIZE
//        }
//    }

    override fun onSortItemSelected(key: String) {
        viewModel.onSort(key)
//        recyclerView.smoothScrollToPosition(0)
    }
}