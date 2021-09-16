package com.shahryar.cryptoprice.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.shahryar.cryptoprice.databinding.SettingsFragmentBinding
import com.shahryar.cryptoprice.viewModel.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private val mViewModel: SettingsViewModel by inject()
    private lateinit var mBinding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = SettingsFragmentBinding.inflate(inflater)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = mViewModel

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListeners()
        mViewModel.apiKey.observe(viewLifecycleOwner, {
            apikeyEditText.setText(it)
        })
    }

    override fun onPause() {
        mViewModel.saveApiKey(apikeyEditText.text.toString())
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

