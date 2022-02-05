package com.dj.core.util.event

import android.content.Context
import android.view.View
import android.widget.Toast
import com.dj.ui.progressDialog.ProgressDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

object UiEventUtil {
    fun showToast(message: String, context: Context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showAlert(
        message: String,
        context: Context,
        title: String = "Alert",
        textPositive: String? = "Ok",
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)

        textNegative?.apply {
            builder.setNegativeButton(textNegative) { dialog, which ->
            }
        }

        if (actionPositive == null) {
            builder.setPositiveButton(textPositive) { dialog, which ->

            }
        } else {
            builder.setPositiveButton(textPositive) { dialog, which ->
                actionPositive()
            }
        }
        builder.setCancelable(false)
        builder.show()
    }

    fun showLoader(show: Boolean, customProgressDialog: ProgressDialog?) {
        if (show) {
            customProgressDialog?.apply {
                if (!isShowing) show()
            }
        } else {
            customProgressDialog?.apply {
                dismiss()
            }
        }
    }

    fun showSnackBar(view: View, message: String, action: (() -> Unit)? = null) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackBar.setAction("Retry") {
                it()
            }
        }
        snackBar.show()
    }

}

