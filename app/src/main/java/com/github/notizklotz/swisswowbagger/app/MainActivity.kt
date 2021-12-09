package com.github.notizklotz.swisswowbagger.app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.github.notizklotz.swisswowbagger.app.components.InsultTargetNameSelector
import com.github.notizklotz.swisswowbagger.app.ui.theme.Red
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme
import com.github.notizklotz.swisswowbagger.app.ui.theme.Yellow

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var fetchInsultIdlingResource: CountingIdlingResource? = null

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val insult = viewModel.insult.observeAsState(getString(R.string.insult_initial))
            viewModel.insult.observe(this) {
                fetchInsultIdlingResource?.decrement()
            }
            val name = viewModel.name.observeAsState("")

            createInsultAudioObserver()

            SwissWowbaggerAppTheme {
                BottomSheetScaffold(
                    floatingActionButton = { InsultButton {
                        fetchInsultIdlingResource?.increment()
                        viewModel.fetchInsult()
                    } },
                    floatingActionButtonPosition = FabPosition.Center,
                    sheetContent = {
                        Spacer(Modifier.height(32.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                        ) {
                            InsultTargetNameSelector(name.value) { viewModel.name.value = it }
                        }
                    },
                    sheetPeekHeight = 100.dp,
                    sheetGesturesEnabled = false,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        InsultText(insult.value)
                    }
                }
            }
        }
    }

    private fun createInsultAudioObserver() {
        val insultAudioUrlObserver = Observer<String> { url ->
            InsultSpeechPlayer.play(url)
        }
        viewModel.insultAudioUrl.observe(this, insultAudioUrlObserver)
    }


    override fun onPause() {
        InsultSpeechPlayer.releaseMediaPlayer()

        super.onPause()
    }

    override fun onStop() {
        InsultSpeechPlayer.releaseMediaPlayer()

        super.onStop()
    }

    @VisibleForTesting
    internal fun getIdlingResource(): IdlingResource {
        if (fetchInsultIdlingResource == null) {
            fetchInsultIdlingResource = CountingIdlingResource("insultfetch")
        }
        return fetchInsultIdlingResource!!
    }

}

@Composable
fun InsultButton(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        onClick = { onClick() },
        text = { Text(stringResource(R.string.insult_button_text)) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_bubble_24),
                contentDescription = null // decorative element
            )
        }
    )
}

@Composable
fun InsultText(text: String) {
    Text(
        text = text,
        color = if (MaterialTheme.colors.isLight) Red else Yellow,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
    )
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DefaultPreview() {
    SwissWowbaggerAppTheme {
        Column {
            InsultText("nö")
            InsultButton { }
        }
    }
}
