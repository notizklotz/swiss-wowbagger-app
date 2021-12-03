package com.github.notizklotz.swisswowbagger.app.widget

import android.content.Context
import com.github.notizklotz.swisswowbagger.app.R

object InstantInsultWidgetSettingsRepository {

    private const val PREFS_NAME = "com.github.notizklotz.swisswowbagger.app.widget.InstantInsultWidget"
    private const val PREF_PREFIX_KEY = "appwidget_"

    internal fun saveNamePref(context: Context, appWidgetId: Int, text: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
        prefs.apply()
    }

    internal fun loadNamePref(context: Context, appWidgetId: Int): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0)
        val nameValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
        return nameValue ?: context.getString(R.string.appwidget_text)
    }

    internal fun deleteNamePref(context: Context, appWidgetId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
        prefs.remove(PREF_PREFIX_KEY + appWidgetId)
        prefs.apply()
    }

}
