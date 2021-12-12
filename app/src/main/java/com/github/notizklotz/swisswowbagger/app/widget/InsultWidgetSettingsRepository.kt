package com.github.notizklotz.swisswowbagger.app.widget

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.github.notizklotz.swisswowbagger.app.logDebug

private const val PREFS_NAME = "com.github.notizklotz.swisswowbagger.app.widget.InsultWidget"
private const val PREF_PREFIX_KEY = "insultwidget_"

private fun getSharedPreferences(context: Context) =
    context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

internal fun saveInsultTargetName(context: Context, appWidgetId: Int, text: String) {
    val key = "$PREF_PREFIX_KEY$appWidgetId"

    logDebug { "saveInsultTargetName: $key: $text" }

    getSharedPreferences(context).edit(commit = true) {
        putString(key, text)
    }
}


internal fun loadInsultTargetName(context: Context, appWidgetId: Int): String {
    val key = "$PREF_PREFIX_KEY$appWidgetId"
    val name = getSharedPreferences(context).getString(key, null) ?: ""

    logDebug { "loadInsultTargetName: $key: $name" }

    return name
}

internal fun deleteInsultTargetName(context: Context, appWidgetId: Int) {
    val key = "$PREF_PREFIX_KEY$appWidgetId"

    logDebug { "deleteInsultTargetName: $key" }

    getSharedPreferences(context).edit(commit = false) {
        remove(key)
    }
}
