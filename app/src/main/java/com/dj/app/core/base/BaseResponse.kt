package com.dj.app.core.base

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName(value = "isSuccessful")
    val isSuccessful: Boolean = false

    @SerializedName(value = "message")
    val message: String = ""

}