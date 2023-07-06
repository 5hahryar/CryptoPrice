package com.shahryar.shared.ui.screens.prices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.shahryar.shared.SharedRes
import com.shahryar.shared.data.model.Currency
import com.shahryar.shared.ui.screens.prices.currency.CurrencyBottomSheet
import com.shahryar.shared.ui.screens.settings.SettingsScreen
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

object PricesScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sheetNavigator = LocalBottomSheetNavigator.current
        val screenModel = rememberScreenModel { PricesScreenModel() }
        val uiState by screenModel.uiState.collectAsState(PricesScreenModel.UiState.Loading)

        PricesScreenContent(
            uiState = uiState,
            onSettingsActionClicked = { navigator.push(SettingsScreen) },
            onCurrencyClicked = { sheetNavigator.show(CurrencyBottomSheet(it)) }
        )
    }
}

@Composable
private fun PricesScreenContent(
    uiState: PricesScreenModel.UiState,
    onSettingsActionClicked: () -> Unit,
    onCurrencyClicked: (currency: Currency) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pricesListState = rememberLazyListState()
    val hasAppBarElevation by derivedStateOf {
        pricesListState.canScrollBackward
    }
    val isScrollToTopVisible by remember {
        derivedStateOf { pricesListState.firstVisibleItemIndex > 0 }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = if (hasAppBarElevation) AppBarDefaults.TopAppBarElevation else 0.dp,
                title = {
                    Text(
                        text = stringResource(SharedRes.strings.app_name),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Medium
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsActionClicked) {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(visible = isScrollToTopVisible, enter = fadeIn(), exit = fadeOut()) {
                FloatingActionButton(
                    onClick = { scope.launch { pricesListState.animateScrollToItem(0) } }
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            when (uiState) {
                is PricesScreenModel.UiState.ApiKeyNotAvailable -> NoApiKeyError({}, {})
                is PricesScreenModel.UiState.Error -> Text(uiState.message)
                is PricesScreenModel.UiState.Loading -> CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                is PricesScreenModel.UiState.Success -> {
                    Prices(
                        currencies = uiState.prices,
                        listState = pricesListState,
                        onCurrencyClicked = onCurrencyClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun NoApiKeyError(onGetKeyClicked: () -> Unit, onEnterKeyClicked: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(SharedRes.strings.no_api_key_message), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            OutlinedButton(onClick = onEnterKeyClicked) {
                Text(text = stringResource(SharedRes.strings.enter_api_key))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = onGetKeyClicked, colors = ButtonDefaults.buttonColors()
            ) { Text(stringResource(SharedRes.strings.get_api_key), color = Color.White) }
        }
    }
}

@Composable
fun Prices(
    currencies: List<Currency>,
    listState: LazyListState = rememberLazyListState(),
    onCurrencyClicked: (currency: Currency) -> Unit
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(10.dp, 15.dp, 10.dp, 15.dp),
        verticalArrangement = Arrangement.spacedBy(17.dp),
    ) {
        items(currencies) { item ->
            PriceExtendedCard(item = item) {
                onCurrencyClicked(item)
            }
        }
    }
}
