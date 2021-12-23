package com.github.notizklotz.swisswowbagger.app

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

const val LOGGING_TAG = "wowbagger"

internal fun logDebug(msgProvider: () -> String) {
    if (Log.isLoggable(LOGGING_TAG, Log.DEBUG)) {
        Log.d(LOGGING_TAG, msgProvider())
    }
}

internal fun logError(msgProvider: () -> Pair<String, Throwable?>) {
    val (msg, throwable) = msgProvider()

    with (FirebaseCrashlytics.getInstance()) {
        log(msg)
        throwable?.also { recordException(it) }
    }

    if (throwable != null) {
        Log.e(LOGGING_TAG, msg, throwable)
    } else {
        Log.e(LOGGING_TAG, msg)
    }
}