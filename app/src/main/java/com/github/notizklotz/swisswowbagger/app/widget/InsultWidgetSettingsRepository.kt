package com.github.notizklotz.swisswowbagger.app.widget

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

private const val PREFS_NAME = "com.github.notizklotz.swisswowbagger.app.widget.InsultWidget"
private const val PREF_PREFIX_KEY = "insultwidget_"

private fun getSharedPreferences(context: Context) = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

internal fun saveInsultTargetName(context: Context, appWidgetId: Int, text: String) {
    getSharedPreferences(context).edit(commit = true) {
        putString("$PREF_PREFIX_KEY$appWidgetId", text)
    }
}


internal fun loadInsultTargetName(context: Context, appWidgetId: Int): String {
    return getSharedPreferences(context).getString("$PREF_PREFIX_KEY$appWidgetId", null) ?: ""
}

internal fun deleteInsultTargetName(context: Context, appWidgetId: Int) {
    getSharedPreferences(context).edit(commit = true) {
        remove("$PREF_PREFIX_KEY$appWidgetId")
    }
}
