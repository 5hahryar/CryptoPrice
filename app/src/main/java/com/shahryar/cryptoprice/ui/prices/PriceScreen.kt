package com.shahryar.cryptoprice.ui.prices

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
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
import com.shahryar.shared.data.model.CurrencyDto
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterialApi
@Composable
fun PriceScreen(navController: NavController) {

    val priceViewModel = getViewModel<PriceViewModel>()
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    MaterialTheme {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                priceViewModel.selectedCurrency?.let {
                    CurrencyBottomSheetView(currency = it)
                }
                Text(text = "")
            },
            sheetShape = androidx.compose.material.MaterialTheme.shapes.large
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                PriceTopBar {
                    navController.navigate("settingsScreen")
                }
                Scaffold(Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
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
                            Prices(currencies = prices, onCurrencyClicked = { clickedCurrency ->
                                coroutineScope.launch {
                                    priceViewModel.selectCurrency(clickedCurrency)
                                    sheetState.show()
                                }
                            })
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
    currencies: List<CurrencyDto>,
    onCurrencyClicked: (currency: CurrencyDto) -> Unit
) {
    LazyColumn {
        items(currencies) { item ->
            PriceItem(item = item, onCurrencyClicked)
        }
    }
}

@Composable
fun PriceTopBar(elevation: Dp = 0.dp, onSettingsActionClicked: () -> Unit) {
    TopAppBar(
        elevation = elevation,
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
                    contentDescription = stringResource(R.string.menu),
                    tint = colorResource(id = R.color.onPrimary)
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