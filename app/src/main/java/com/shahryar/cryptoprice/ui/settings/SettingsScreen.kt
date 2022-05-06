package com.shahryar.cryptoprice.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shahryar.cryptoprice.R
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(navController: NavController) {

    val settingsViewModel = getViewModel<SettingsViewModel>()
    val apiKey = settingsViewModel.apiKey.observeAsState().value ?: ""

    Column {
        SettingsTopBar({
            settingsViewModel.saveApiKey()
            navController.navigateUp()
        }, {
            navController.navigateUp()
        })
        SettingsContent(apiKey) {
            settingsViewModel.onApiKeyChange(it)
        }
    }
}

@Composable
private fun SettingsTopBar(onSaveClicked: () -> Unit, onBackClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Settings", color = colorResource(id = R.color.onPrimary))
        },
        backgroundColor = colorResource(id = R.color.primary),
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                androidx.compose.material.Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.onPrimary)
                )
            }
        },
        actions = {
            IconButton(onSaveClicked) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Save",
                    tint = colorResource(id = R.color.onPrimary)
                )
            }
        }
    )
}

@Composable
private fun SettingsContent(apiKey: String, onApiKeyChanged: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            label = { Text(text = "API Key") },
            modifier = Modifier
                .fillMaxWidth(),
            value = apiKey,
            onValueChange = onApiKeyChanged
        )
    }
}

@Composable
@Preview
private fun Preview() {
//    SettingsScreen()
}