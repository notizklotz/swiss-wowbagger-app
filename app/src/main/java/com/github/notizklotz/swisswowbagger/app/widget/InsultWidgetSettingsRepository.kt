package com.github.notizklotz.swisswowbagger.app.widget

import android.content.Context


private const val PREFS_NAME = "com.github.notizklotz.swisswowbagger.app.widget.InsultWidget"
private const val PREF_PREFIX_KEY = "insultwidget_"

internal fun saveInsultTargetName(context: Context, appWidgetId: Int, text: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.putString("$PREF_PREFIX_KEY$appWidgetId", text)
    prefs.apply()
}

internal fun loadInsultTargetName(context: Context, appWidgetId: Int): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    return prefs.getString("$PREF_PREFIX_KEY$appWidgetId", null) ?: ""
}

internal fun deleteInsultTargetName(context: Context, appWidgetId: Int) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.remove("$PREF_PREFIX_KEY$appWidgetId")
    prefs.apply()
}
