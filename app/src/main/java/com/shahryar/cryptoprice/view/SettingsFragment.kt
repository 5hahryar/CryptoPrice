package com.shahryar.cryptoprice.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shahryar.cryptoprice.R
import com.shahryar.cryptoprice.viewModel.SettingsViewModel
import com.shahryar.cryptoprice.viewModel.SettingsViewModelFactory

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(requireContext())).get(
            SettingsViewModel::class.java
        )
        
        return ComposeView(requireContext()).apply { setContent { SettingsCompose() } }
    }

    @Composable
    fun SettingsCompose() {
        Column {
            TopAppBar(
                title = { Text(text = "Settings", color = colorResource(id = R.color.onPrimary)) },
                navigationIcon = {
                    IconButton(onClick = { activity?.onBackPressed() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back_24px),
                            contentDescription = "Back",
                            tint = colorResource(id = R.color.onPrimary))
                    }
                },
                backgroundColor = colorResource(id = R.color.primary)
            )
            Body()
        }
    }

    @Composable
    fun Body() {
        val apiKey = viewModel.apiKey.value
        Column(modifier = Modifier.padding(20.dp)) {
            OutlinedTextField(
                value = apiKey,
                onValueChange = { viewModel.onApiKeyChanged(it) },
                label = { Text("API key")},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }

    @Composable
    @Preview
    fun SettingsPreview() {
        SettingsCompose()
    }

    override fun onPause() {
        viewModel.saveApiKey()
        super.onPause()
    }

}

