package com.dj.app.core.util.event

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed class UiEvent {
    class ShowLoader(val show: Boolean) : UiEvent()
    class ShowToast(val message: String) : UiEvent()
    class ShowSnackBar(val message: String, val action: (() -> Unit)? = null) : UiEvent()
    class ShowAlert(
        val title: String = "Alert",
        val message: String,
        val textPositive: String = "Ok",
        val textNegative: String? = null,
        val actionPositive: (() -> Unit)? = null
    ) : UiEvent()

    class ShowBottomSheet(
        val bottomSheetFragment: BottomSheetDialogFragment,
        val tag: String? = null
    ) : UiEvent()
}