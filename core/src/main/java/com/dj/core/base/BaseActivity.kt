package com.dj.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.dj.core.util.event.UiEvent
import com.dj.core.util.event.UiEventUtil
import com.dj.ui.progressDialog.ProgressDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {
    private var customProgressDialog: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customProgressDialog = ProgressDialog(this)
    }

    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                baseViewModel.uiEvents.collect { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            UiEventUtil.showAlert(event.message, this@BaseActivity)
                        }
                        is UiEvent.ShowToast -> {
                            UiEventUtil.showToast(event.message, this@BaseActivity)
                        }
                        is UiEvent.ShowLoader -> {
                            UiEventUtil.showLoader(event.show, customProgressDialog)
                        }
                        is UiEvent.ShowSnackBar -> {
                            UiEventUtil.showSnackBar(
                                findViewById(android.R.id.content),
                                event.message,
                                event.action
                            )
                        }
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        if (customProgressDialog != null) {
            customProgressDialog = null
        }
        super.onDestroy()
    }
}