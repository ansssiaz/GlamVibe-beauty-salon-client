package com.glamvibe.glamvibeclient.utils

import android.content.Context
import com.glamvibe.glamvibeclient.R
import java.io.IOException

fun Throwable.getErrorText(context: Context): String = when (this) {
    is IOException -> context.getString(R.string.network_error)
    else -> context.getString(R.string.unknown_error)
}