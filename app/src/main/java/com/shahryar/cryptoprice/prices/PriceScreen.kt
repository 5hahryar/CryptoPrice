package com.shahryar.cryptoprice.prices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.data.model.Currency
import com.shahryar.shared.Greeting
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterialApi
@Composable
fun PriceScreen(navController: NavController) {
    val priceViewModel = getViewModel<PriceViewModel>()
    val uiState: PriceViewModel.UiState by priceViewModel.uiState.observeAsState(PriceViewModel.UiState.Loading)
    val selectedCurrency = priceViewModel.selectedCurrency.observeAsState()
    val isRefreshing: Boolean by priceViewModel.isRefreshing.observeAsState(true)
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    MaterialTheme {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = {
                if (selectedCurrency.value == null) {
                    Text(text = "Choose a currency to display")
                } else {
                    CurrencyBottomSheetView(currency = selectedCurrency.value!!)
                }
            },
            sheetShape = androidx.compose.material.MaterialTheme.shapes.large
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)) {
                PriceTopBar {
                    navController.navigate("settingsScreen")
                }
                Scaffold(Modifier.fillMaxSize()) {
                    SwipeRefresh(
                        modifier = Modifier.fillMaxSize(),
                        state = SwipeRefreshState(isRefreshing),
                        onRefresh = { priceViewModel.refreshData() }) {
//                        Surface(
//                            Modifier
//                                .fillMaxSize()
//                                .background(color = Color.Blue)) {
//                            val state = priceViewModel.uiState.observeAsState().value
//                            if (state is PriceViewModel.UiState.Success) Text(text = state.currencies.toString())
//                        }
                    when (uiState) {
                        is PriceViewModel.UiState.Error -> {
                            Text(text = (uiState as PriceViewModel.UiState.Error).message)
                        }
                        is PriceViewModel.UiState.Success -> {
                            Prices((uiState as PriceViewModel.UiState.Success).currencies) {
                                coroutineScope.launch {
//                                    priceViewModel.selectCurrency(it)
//                                    sheetState.show()
                                }
                            }
                        }
                        else -> {}
                    }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Prices(currencies: List<com.shahryar.shared.data.model.Currency>, onCurrencyClicked: (currency: com.shahryar.shared.data.model.Currency) -> Unit) {
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
                    contentDescription = "Menu",
                    tint = colorResource(id = R.color.onPrimary)
                )
            }
        }
    )
}