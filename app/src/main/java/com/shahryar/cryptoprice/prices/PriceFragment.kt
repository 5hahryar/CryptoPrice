package com.shahryar.cryptoprice.prices

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.prices.viewmodel.PriceViewModel
import org.koin.android.ext.android.inject

class PriceFragment : Fragment() {

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

    @Composable
    fun PriceFragmentView() {
        MaterialTheme() {
            Column {
                PriceTopAppBar()
                PriceContent(mViewModel.currencies.observeAsState().value)
            }
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
        SmallTopAppBar(
            title = {
                Text(
                    text = getString(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.onPrimary)
                )
            },
            actions = {
                IconButton(onClick = { findNavController().navigate(R.id.action_priceFragment_to_settingsFragment) }) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Menu",
                        tint = colorResource(id = R.color.onPrimary)
                    )
                }
            }
        )
    }

    @Composable
    @Preview
    fun Preview() {
        PriceTopAppBar()
        PriceFragmentView()
    }
}