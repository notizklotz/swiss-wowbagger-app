package com.github.notizklotz.swisswowbagger.app

import android.util.Log

const val LOGGING_TAG = "wowbagger"

internal fun logDebug(msgProvider: () -> String) {
    if (Log.isLoggable(LOGGING_TAG, Log.DEBUG)) {
        Log.d(LOGGING_TAG, msgProvider())
    }
}

internal fun logError(msgProvider: () -> Pair<String, Throwable?>) {
    if (Log.isLoggable(LOGGING_TAG, Log.ERROR)) {
        val (msg, throwable) = msgProvider()

        if (throwable != null) {
            Log.e(LOGGING_TAG, msg, throwable)
        } else {
            Log.e(LOGGING_TAG, msg)
        }
    }
}