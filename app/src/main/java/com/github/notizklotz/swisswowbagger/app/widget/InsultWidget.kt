package com.github.notizklotz.swisswowbagger.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.app.PendingIntent

import android.content.Intent
import com.github.notizklotz.swisswowbagger.app.InsultSpeechPlayer
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.data.InsultRepository
import kotlinx.coroutines.*

/**
 * Widget for instantly insulting a preconfigured target name.
 * App Widget Configuration implemented in [InsultWidgetConfigureActivity].
 */
class InsultWidget : AppWidgetProvider() {

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
            deleteInsultTargetName(context, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == INTENT_ACTION_INSTANT_INSULT_PLAY) {

            val appWidgetId = intent.getIntExtra(INTENT_EXTRA_APP_WIDGET_ID, -1)

            if (appWidgetId != -1) {
                val insultTargetName = loadInsultTargetName(context, appWidgetId)

                runBlocking {
                    val insult = InsultRepository.getInsult(listOf(insultTargetName))

                    InsultSpeechPlayer.play(insult.getAudioUrl(listOf(insultTargetName)))
                }
            }
        }

        super.onReceive(context, intent)
    }
}

private const val INTENT_ACTION_INSTANT_INSULT_PLAY = "INSTANT_INSULT_PLAY"
private const val INTENT_EXTRA_APP_WIDGET_ID = "appWidgetId"

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, InsultWidget::class.java)
    intent.action = INTENT_ACTION_INSTANT_INSULT_PLAY
    intent.putExtra(INTENT_EXTRA_APP_WIDGET_ID, appWidgetId)

    val actionPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val widgetText = loadInsultTargetName(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.instant_insult_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)
    views.setOnClickPendingIntent(R.id.appwidget_button, actionPendingIntent)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
