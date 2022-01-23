package com.dj.app.core.util.result

sealed class ResultState<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : ResultState<T>(data)
    class Success<T>(data: T? = null, message: String) : ResultState<T>(data, message)
    class Error<T>(message: String, data: T? = null) : ResultState<T>(data, message)
}