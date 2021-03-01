package com.shahryar.cryptoprice.view

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.DialogFragment
import com.shahryar.cryptoprice.R
import kotlinx.android.synthetic.main.dialog_about.view.*

class AboutDialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_about, null)
            view.textView_repo.movementMethod = LinkMovementMethod.getInstance()
            view.textView_email.movementMethod = LinkMovementMethod.getInstance()
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}