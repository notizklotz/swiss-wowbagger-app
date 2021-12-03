package com.github.notizklotz.swisswowbagger.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.app.PendingIntent

import android.content.Intent
import com.github.notizklotz.swisswowbagger.app.InsultSpeechPlayer
import com.github.notizklotz.swisswowbagger.app.R

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [InstantInsultWidgetConfigureActivity]
 */
class InstantInsultWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            InstantInsultWidgetSettingsRepository.deleteNamePref(context, appWidgetId)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == INSTANT_INSULT_PLAY) {
            InsultSpeechPlayer.play("https://wowbagger.schaltstelle.ch/1638460098599?format=wav&v=undefined&names=%C3%84du")
        }

        super.onReceive(context, intent)
    }
}

private const val INSTANT_INSULT_PLAY = "INSTANT_INSULT_PLAY"

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, InstantInsultWidget::class.java)
    intent.action = INSTANT_INSULT_PLAY
    val actionPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val widgetText = InstantInsultWidgetSettingsRepository.loadNamePref(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.instant_insult_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setOnClickPendingIntent(R.id.appwidget_button, actionPendingIntent)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
