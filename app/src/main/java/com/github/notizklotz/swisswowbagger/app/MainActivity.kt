package com.github.notizklotz.swisswowbagger.app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.github.notizklotz.swisswowbagger.app.components.InsultTargetNameSelector
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userData = viewModel.insult.observeAsState(getString(R.string.insult_initial))
            val name = viewModel.name.observeAsState("")

            createInsultAudioObserver()

            SwissWowbaggerAppTheme {
                BottomSheetScaffold(
                    floatingActionButton = { InsultButton { viewModel.fetchInsult() } },
                    floatingActionButtonPosition = FabPosition.Center,
                    sheetContent = {
                        Spacer(Modifier.height(8.dp))
                        InsultTargetNameSelector(name.value) { viewModel.name.value = it }
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
                        InsultText(userData.value)
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
}

@Composable
fun InsultButton(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        onClick = { onClick() },
        text = { Text(stringResource(R.string.insult_button_text)) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_emoji_symbols_24),
                contentDescription = null // decorative element
            )
        }
    )
}

@Composable
fun InsultText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
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