package com.github.notizklotz.swisswowbagger.app.widget

import android.appwidget.AppWidgetManager
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.github.notizklotz.swisswowbagger.app.components.InsultTargetNameSelector
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme

/**
 * Widget configuration screen for [InsultWidget].
 */
class InsultWidgetConfigureActivity : ComponentActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    private val viewModel: InsultWidgetConfigureActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        viewModel.insultTargetName.value = loadInsultTargetName(this, appWidgetId)

        setContent {
            val name = viewModel.insultTargetName.observeAsState("")

            SwissWowbaggerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        InsultTargetNameSelector(
                            preselectedName = name.value,
                            onNameSelected = { viewModel.insultTargetName.value = it })
                        TextButton(onClick = { saveTargetNameAndFinishActivity() }) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }

    private fun saveTargetNameAndFinishActivity() {
        saveInsultTargetName(
            this,
            appWidgetId,
            viewModel.insultTargetName.value ?: ""
        )

        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        updateAppWidget(this, appWidgetManager, appWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwissWowbaggerAppTheme {
        Column {
            InsultTargetNameSelector(preselectedName = "", onNameSelected = {})
            TextButton(onClick = { }) {
                Text(text = "OK")
            }
        }
    }
}