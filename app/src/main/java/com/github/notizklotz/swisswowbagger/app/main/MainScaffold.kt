package com.github.notizklotz.swisswowbagger.app.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.data.Insult
import com.github.notizklotz.swisswowbagger.app.data.Voice
import com.github.notizklotz.swisswowbagger.app.getWebsiteBaseUrl
import com.github.notizklotz.swisswowbagger.app.ui.theme.Red
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme
import com.github.notizklotz.swisswowbagger.app.ui.theme.Yellow

/**
 * Height of the FAB overlapping the bottom sheet.
 * := Half of [FloatingActionButton].ExtendedFabSize
 */
private val fabHeightInSheet = 24.dp

@Composable
fun MainScaffold(
    scaffoldState: ScaffoldState,
    insult: Insult?,
    name: String,
    voice: Voice,
    loading: Boolean,
    onInsultClicked: () -> Unit,
    onNameChange: (String) -> Unit,
    onVoiceChange: (Voice) -> Unit
) {
    val context = LocalContext.current

    SwissWowbaggerAppTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                InsultFloatingActionButton(onInsultClicked)
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                MainAppBar {
                    val text: String = insult
                        ?.let { "${insult.text}\n\n${insult.getWebsiteUrl(voice)}" }
                        ?: getWebsiteBaseUrl()

                    ShareCompat.IntentBuilder(context)
                        .setType("text/plain")
                        .setText(text)
                        .apply { insult?.text?.let { setSubject(it) } }
                        .startChooser()
                }
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(weight = 1f, fill = true)
                ) {
                    InsultText(insult?.text ?: stringResource(id = R.string.insult_initial))
                }
                Surface(elevation = 2.dp) {
                    MainUserInput(
                        name,
                        voice,
                        loading,
                        Modifier
                            .padding(bottom = fabHeightInSheet)
                            .padding(8.dp),
                        onNameChange,
                        onVoiceChange
                    )
                }
            }
        }
    }
}

@Composable
private fun MainAppBar(onShareClick: () -> Unit) {
    val openInfoDialog = remember { mutableStateOf(false) }

    TopAppBar {
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = onShareClick) {
            Icon(Icons.Filled.Share, contentDescription = "Teile")
        }
        IconButton(onClick = { openInfoDialog.value = true }) {
            Icon(Icons.Filled.Info, contentDescription = "Info")
        }
    }

    if (openInfoDialog.value) {
        InfoDialog { openInfoDialog.value = false }
    }
}

@Composable
private fun InsultFloatingActionButton(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        onClick = { onClick() },
        text = { Text(stringResource(R.string.insult_button_text)) },
        modifier = Modifier.testTag(TEST_TAG_INSULT_BUTTON),
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_bubble_24),
                contentDescription = null // decorative element
            )
        }
    )
}

@Composable
private fun InsultText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = if (MaterialTheme.colors.isLight) Red else Yellow,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = modifier
            .fillMaxWidth()
            .testTag(TEST_TAG_INSULT_TEXT)
    )
}

const val TEST_TAG_INSULT_TEXT = "InsultText"
const val TEST_TAG_INSULT_BUTTON = "InsultButton"

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DefaultPreview() {
    MainScaffold(
        scaffoldState = rememberScaffoldState(),
        insult = null,
        name = "",
        loading = true,
        voice = Voice.exilzuerchere,
        onInsultClicked = { },
        onNameChange = {},
        onVoiceChange = {})
}