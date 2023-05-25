package com.shahryar.shared.ui.prices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.value.getValue
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@Composable
fun PricesScreen(component: PricesComponent, onNavigateToSettings: () -> Unit) {
    val uiState by component.uiState
    val pricesState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTopButton by remember {
        derivedStateOf { pricesState.firstVisibleItemIndex > 0 }
    }

    PricesScreenContent(uiState, showScrollToTopButton, onNavigateToSettings)
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PricesScreenContent(uiState: PricesComponent.UiState, showScrollToTopButton: Boolean, onSettingsActionClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CryptoPrice",
                        style = MaterialTheme.typography.h1,
                        color = MaterialTheme.colors.onPrimary
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
            AnimatedVisibility(visible = showScrollToTopButton) {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
        }
    }
}