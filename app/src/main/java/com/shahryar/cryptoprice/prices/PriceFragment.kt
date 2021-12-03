package com.shahryar.cryptoprice.prices

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                PriceFragmentView(mViewModel.uiState.observeAsState())
            }

            mViewModel.toastMessage.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    @Composable
    fun PriceFragmentView(uiState: State<PriceViewModel.UiState?>) {
        MaterialTheme {
            Column {
                PriceTopAppBar()
                PriceContent(uiState)
            }
        }
    }

    @Composable
    private fun PriceContent(uiState: State<PriceViewModel.UiState?>) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            state = rememberSwipeRefreshState(isRefreshing = uiState.value?.isRefreshing ?: true),
            onRefresh = { mViewModel.refreshData() }) {
            if (!uiState.value?.currencies.isNullOrEmpty()) {
                LazyColumn {
                    items(uiState.value?.currencies!!) { item ->
                        PriceItemView(item = item)
                    }
                }
            } else if (uiState.value?.isApiKeyAvailable == false) {
                NoApiKeyView()
            } else {
                Text(text = uiState.value?.errorMessage ?: "Error loading data")
            }
        }
    }

    @Composable
    private fun NoApiKeyView() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = getString(R.string.no_api_key_message), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier) {
                Button(onClick = {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://coinmarketcap.com/api/pricing/")
                        )
                    )
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text(text = getString(R.string.get_api_key))
                }
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedButton(
                    onClick = { findNavController().navigate(R.id.action_priceFragment_to_settingsFragment) }) {
                    Text(text = getString(R.string.enter_api_key), color = Color.Black)
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
                    fontWeight = FontWeight.Medium,
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
        NoApiKeyView()
    }
}