package com.dj.app.main.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.dj.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel() {

    private val _endSplash: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val endSplash = _endSplash.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _endSplash.value = false
        }
    }
}