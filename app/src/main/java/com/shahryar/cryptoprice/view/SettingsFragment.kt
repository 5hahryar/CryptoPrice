package com.shahryar.cryptoprice.view

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.shahryar.cryptoprice.databinding.SettingsFragmentBinding
import com.shahryar.cryptoprice.viewModel.SettingsViewModel
import com.shahryar.cryptoprice.viewModel.SettingsViewModelFactory
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, SettingsViewModelFactory(requireContext())).get(
            SettingsViewModel::class.java
        )
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        viewModel.apiKey.observe(viewLifecycleOwner, {
            apikeyEditText.setText(it)
        })
    }

    override fun onPause() {
        viewModel.saveApiKey(apikeyEditText.text.toString())
        super.onPause()
    }

    private fun setListeners() {
        apikeyLayout.setOnLongClickListener {
            apikeyEditText.isEnabled = true
            showSoftKeyboard(apikeyEditText)
            true
        }

        topAppBar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}

