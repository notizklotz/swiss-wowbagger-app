package com.github.notizklotz.swisswowbagger.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID
import android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.components.InsultTargetNameSelector
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme
import kotlinx.coroutines.DelicateCoroutinesApi

/**
 * Widget configuration screen for [InsultWidget].
 */
class InsultWidgetConfigureActivity : ComponentActivity() {

    private val viewModel: InsultWidgetConfigureActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        // Find the widget id from the intent.
        val appWidgetId = intent.extras?.getInt(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID)
            ?: INVALID_APPWIDGET_ID

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        viewModel.insultTargetName.value =  loadInsultTargetName(this, appWidgetId)

        setContent {
            WidgetConfigureForm(
                name = viewModel.insultTargetName.value,
                appWidgetId = appWidgetId,
                onNameChange = { viewModel.insultTargetName.value = it },
                onNameConfirm = { saveTargetNameAndFinishActivity(it) }
            )
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun saveTargetNameAndFinishActivity(appWidgetId: Int) {
        saveInsultTargetName(
            this,
            appWidgetId,
            viewModel.insultTargetName.value
        )

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        updateAppWidget(this, appWidgetManager, appWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}

@Composable
private fun WidgetConfigureForm(
    name: String,
    appWidgetId: Int,
    onNameChange: (name: String) -> Unit,
    onNameConfirm: (appWidgetId: Int) -> Unit
) {
    SwissWowbaggerAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                InsultTargetNameSelector(
                    name = name,
                    onNameChange = onNameChange
                )
                TextButton(onClick = { onNameConfirm(appWidgetId) }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WidgetConfigureForm(
        name = "Ädu",
        appWidgetId = 0,
        onNameChange = { },
        onNameConfirm = { }
    )
}