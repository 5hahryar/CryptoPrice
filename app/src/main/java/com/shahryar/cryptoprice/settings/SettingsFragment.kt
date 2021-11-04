package com.shahryar.cryptoprice.settings

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
        Column {
            TopAppBarView()
            ContentView()
        }
    }

    @Composable
    fun TopAppBarView() {
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
            }
        )
    }

    @Composable
    fun ContentView() {
        var text = mViewModel.apiKey.observeAsState().value?.apiKey ?: ""

        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                label = { Text(text = "API Key") },
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    mViewModel.saveApiKey(it)
                })
        }
    }

    @Composable
    @Preview
    fun Preview() {
        SettingsView()
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        setListeners()
//        mViewModel.apiKey.observe(viewLifecycleOwner, {
//            apikeyEditText.setText(it.apiKey)
//        })
//    }
//
//    override fun onPause() {
//        mViewModel.saveApiKey(apikeyEditText.text.toString())
//        super.onPause()
//    }
//
//    private fun setListeners() {
//        apikeyLayout.setOnLongClickListener {
//            apikeyEditText.isEnabled = true
//            showSoftKeyboard(apikeyEditText)
//            true
//        }
//
//        topAppBar.setNavigationOnClickListener { activity?.onBackPressed() }
//    }
//
//    private fun showSoftKeyboard(view: View) {
//        if (view.requestFocus()) {
//            val imm: InputMethodManager =
//            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
//        }
//    }

}

