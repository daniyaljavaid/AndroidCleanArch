package com.dj.app.core.util.result

sealed class ResultState<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : ResultState<T>()
    class Success<T>(data: T? = null, message: String? = null) : ResultState<T>(data, message)
    class Error<T>(message: String) : ResultState<T>(message = message)
}