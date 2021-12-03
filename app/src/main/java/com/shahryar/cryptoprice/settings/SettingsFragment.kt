package com.shahryar.cryptoprice.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shahryar.cryptoprice.R
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private val mViewModel: SettingsViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SettingsView()
            }
        }
    }

    @Composable
    fun SettingsView() {
        val apiKey = mViewModel.apiKey.observeAsState().value ?: ""

        Column {
            TopAppBarView {
                mViewModel.saveApiKey()
                findNavController().navigateUp()
            }
            ContentView(apiKey) {
                mViewModel.onApiKeyChange(it)
            }
        }
    }

    @Composable
    fun TopAppBarView(onSaveClicked: () -> Unit) {
        TopAppBar(
            title = {
                Text(text = "Settings", color = colorResource(id = R.color.onPrimary))
            },
            backgroundColor = colorResource(id = R.color.primary),
            navigationIcon = {
                IconButton(onClick = { findNavController().navigateUp() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colorResource(id = R.color.onPrimary)
                    )
                }
            },
            actions = {
                IconButton(onSaveClicked) {
                    androidx.compose.material3.Icon(
                        Icons.Filled.Check,
                        contentDescription = "Save",
                        tint = colorResource(id = R.color.onPrimary)
                    )
                }
            }
        )
    }

    @Composable
    fun ContentView(apiKey: String, onApiKeyChanged: (String) -> Unit) {
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
                onValueChange = onApiKeyChanged)
        }
    }

    @Composable
    @Preview
    fun Preview() {
        SettingsView()
    }
}

