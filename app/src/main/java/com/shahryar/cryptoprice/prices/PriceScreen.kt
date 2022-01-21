package com.shahryar.cryptoprice.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.cryptoprice.prices.PriceViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
fun PriceScreen(navController: NavController) {
    val priceViewModel = getViewModel<PriceViewModel>()
    val uiState = priceViewModel.uiState.observeAsState()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val currency = remember { mutableStateOf<Currency?>(null) }

    MaterialTheme {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                if (currency.value == null) {
                    Text(text = "Choose a currency to display")
                } else {
                    CurrencyBottomSheetView(currency = currency.value!!)
                }
            },
            sheetShape = androidx.compose.material.MaterialTheme.shapes.large
        ) {
            Column {
                PriceTopBar {
                    navController.navigate("settingsScreen")
                }
                if (uiState.value?.currencies.isNullOrEmpty()) {
                    Text(text = "Error")
                } else Prices(uiState.value?.currencies!!) {
                    scope.launch {
                        currency.value = it
                        sheetState.show()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Prices(currencies: List<Currency>, onCurrencyClicked: (currency: Currency) -> Unit) {

    LazyColumn {
        items(currencies) { item ->
            PriceItem(item = item, onCurrencyClicked)
        }
    }
}

@Composable
fun PriceTopBar(onSettingsActionClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.primary),
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.onPrimary)
            )
        },
        actions = {
            IconButton(onClick = onSettingsActionClicked) {
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
fun PriceItem(item: Currency, onItemClick: (currency: Currency) -> Unit) {
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
                onClick = { onItemClick(item) },
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
@Preview
private fun Preview() {
//    PriceScreen()
}