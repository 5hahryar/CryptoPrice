package com.shahryar.cryptoprice.ui.prices

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.ui.CryptoPriceTheme
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.data.model.CurrencyDto
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun PriceScreen(navController: NavController) {

    val priceViewModel = getViewModel<PriceViewModel>()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val pricesState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val showScrollToTopButton by remember {
        derivedStateOf { pricesState.firstVisibleItemIndex > 0 }
    }

    CryptoPriceTheme {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                priceViewModel.selectedCurrency?.let {
                    CurrencyBottomSheetView(currency = it)
                }
                Text(text = "")
            },
            sheetShape = MaterialTheme.shapes.large,
            sheetBackgroundColor = MaterialTheme.colors.primary,
            scrimColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
        ) {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                PriceTopBar {
                    navController.navigate("settingsScreen")
                }
                Scaffold(
                    Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    floatingActionButton = {
                        if (showScrollToTopButton) {
                            AnimatedVisibility(visible = showScrollToTopButton) {
                                androidx.compose.material3.FloatingActionButton(
                                    containerColor = MaterialTheme.colors.primary,
                                    onClick = {
                                        coroutineScope.launch {
                                            pricesState.animateScrollToItem(
                                                0
                                            )
                                        }
                                    }) {
                                    Icon(
                                        modifier = Modifier.padding(10.dp),
                                        painter = painterResource(id = R.drawable.ic_arrow_upward),
                                        contentDescription = "",
                                        tint = MaterialTheme.colors.onPrimary
                                    )
                                }
                            }
                        }
                    }) {
                    SwipeRefresh(
                        modifier = Modifier.fillMaxSize(),
                        state = SwipeRefreshState(priceViewModel.uiState.isRefreshing),
                        onRefresh = { priceViewModel.refreshPrices() }) {
                        priceViewModel.uiState.error?.let { error ->
                            if (error.isNotEmpty()) {
                                LaunchedEffect(key1 = error) {
                                    val snackResult = scaffoldState.snackbarHostState.showSnackbar(
                                        message = error,
                                        actionLabel = "Retry"
                                    )
                                    if (snackResult == SnackbarResult.ActionPerformed) priceViewModel.refreshPrices()
                                    priceViewModel.errorShown()
                                }
                            }
                        }

                        priceViewModel.uiState.prices?.let { prices ->
                            if (prices.isNotEmpty()) {
                                Prices(
                                    currencies = prices,
                                    pricesState,
                                    onCurrencyClicked = { clickedCurrency ->
                                        coroutineScope.launch {
                                            priceViewModel.selectCurrency(clickedCurrency)
                                            sheetState.show()
                                        }
                                    })
                            } else {
                                Column(
                                    Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Something went wrong!", fontWeight = FontWeight.Medium)
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Button(onClick = { priceViewModel.refreshPrices() }) {
                                        Text(text = "Try again")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Prices(
    currencies: List<Currency>,
    pricesState: LazyListState,
    onCurrencyClicked: (currency: Currency) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp, 15.dp, 10.dp, 15.dp),
        verticalArrangement = Arrangement.spacedBy(17.dp),
        state = pricesState
    ) {
        items(currencies) { item ->
            PriceItem(item = item, onCurrencyClicked)
        }
    }
}

@Composable
fun PriceTopBar(elevation: Dp = 0.dp, onSettingsActionClicked: () -> Unit) {
    TopAppBar(
        elevation = elevation,
        backgroundColor = MaterialTheme.colors.primary,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.onPrimary
            )
        },
        actions = {
            IconButton(onClick = onSettingsActionClicked) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.menu),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    )
}

@Composable
private fun NoApiKeyError(onGetKeyClicked: () -> Unit, onEnterKeyClicked: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.no_api_key_message), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            OutlinedButton(onClick = onEnterKeyClicked) {
                Text(text = stringResource(R.string.enter_api_key))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = onGetKeyClicked, colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(
                        id = R.color.black
                    )
                )
            ) {
                Text(text = stringResource(R.string.get_api_key), color = Color.White)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NoApiKeyError({}, {})
}