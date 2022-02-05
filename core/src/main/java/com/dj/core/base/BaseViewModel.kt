package com.dj.core.base

import androidx.lifecycle.ViewModel
import com.dj.core.util.event.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel() : ViewModel() {
    private val _uiEventsLiveData = MutableSharedFlow<UiEvent>()
    val uiEvents = _uiEventsLiveData.asSharedFlow()

    suspend fun showLoader(show: Boolean) {
        _uiEventsLiveData.emit(UiEvent.ShowLoader(show))
    }

    suspend fun showAlert(
        message: String,
        title: String = "Alert",
        textPositive: String = "Ok",
        textNegative: String? = null,
        actionPositive: (() -> Unit)? = null
    ) {
        _uiEventsLiveData.emit(
            UiEvent.ShowAlert(
                title = title,
                message = message,
                textPositive = textPositive,
                textNegative = textNegative,
                actionPositive = actionPositive
            )
        )
    }

    suspend fun showToast(message: String) {
        _uiEventsLiveData.emit(UiEvent.ShowToast(message))
    }

    suspend fun showSnackBar(message: String, action: (() -> Unit)? = null) {
        _uiEventsLiveData.emit(UiEvent.ShowSnackBar(message, action))
    }

}