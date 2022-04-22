package com.dj.core.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dj.core.util.event.UiEvent
import com.dj.core.util.event.UiEventUtil
import com.dj.ui.progressDialog.ProgressDialog
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {
    private var customProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customProgressDialog = ProgressDialog(requireContext())
    }

    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                baseViewModel.uiEvents.collect { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
                            UiEventUtil.showAlert(event.message, requireContext())
                        }
                        is UiEvent.ShowToast -> {
                            UiEventUtil.showToast(event.message, requireContext())
                        }
                        is UiEvent.ShowLoader -> {
                            UiEventUtil.showLoader(event.show, customProgressDialog)
                        }
                        is UiEvent.ShowSnackBar -> {
                            UiEventUtil.showSnackBar(
                                requireActivity().findViewById(android.R.id.content),
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
        customProgressDialog = null
        super.onDestroy()
    }
}