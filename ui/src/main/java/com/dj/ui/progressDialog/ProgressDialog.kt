package com.dj.ui.progressDialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.dj.ui.R

class ProgressDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.progress_dialog_layout)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}