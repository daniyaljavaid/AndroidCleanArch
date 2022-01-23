package com.dj.app.core.response

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName(value = "isSuccessful")
    val isSuccessful: Boolean = false

    @SerializedName(value = "message")
    val message: String = ""

}