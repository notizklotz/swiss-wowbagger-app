package com.github.notizklotz.swisswowbagger.app.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import android.widget.Toast
import com.github.notizklotz.swisswowbagger.app.InsultSpeechPlayer
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.data.InsultRepository
import com.github.notizklotz.swisswowbagger.app.data.Voice
import com.github.notizklotz.swisswowbagger.app.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Widget for instantly insulting a preconfigured target name.
 * App Widget Configuration implemented in [InsultWidgetConfigureActivity].
 */
class InsultWidget : AppWidgetProvider() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

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
            val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
            if (appWidgetId != -1) {
                playInsult(context, appWidgetId)
            }
        }

        super.onReceive(context, intent)
    }

    private fun playInsult(context: Context, appWidgetId: Int) {

        coroutineScope.launch {
            val insultTargetName = loadInsultTargetName(context, appWidgetId)

            try {
                val insult = InsultRepository.getRandomInsult(insultTargetName)
                InsultSpeechPlayer.play(insult.getAudioUrl(Voice.exilzuerchere))
            } catch (e: Exception) {
                logError { "Could not fetch insult" to e }

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

private const val INTENT_ACTION_INSTANT_INSULT_PLAY = "INSTANT_INSULT_PLAY"

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val intent = Intent(context, InsultWidget::class.java)
    intent.action = INTENT_ACTION_INSTANT_INSULT_PLAY
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

    val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
    } else PendingIntent.FLAG_CANCEL_CURRENT

    val actionPendingIntent = PendingIntent.getBroadcast(
        context,
        appWidgetId,
        intent,
        flags
    )

    val widgetText = loadInsultTargetName(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.instant_insult_widget)
    views.setTextViewText(R.id.appwidget_button, widgetText)
    views.setOnClickPendingIntent(R.id.appwidget_button, actionPendingIntent)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
