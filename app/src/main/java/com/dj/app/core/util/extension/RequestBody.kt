package com.dj.app.core.util.extension

import okhttp3.RequestBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException

fun RequestBody.toRequestString(): String? = try {
    val buffer = Buffer()
    writeTo(buffer)
    buffer.readUtf8()
} catch (e: IOException) {
    Timber.e(e.toString())
    null
}