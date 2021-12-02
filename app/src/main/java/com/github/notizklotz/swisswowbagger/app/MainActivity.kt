package com.github.notizklotz.swisswowbagger.app

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

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
                        NameSelector(name.value) { viewModel.name.value = it }
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
            releaseMediaPlayer()

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    release()
                    mediaPlayer = null
                }
            }
        }
        viewModel.insultAudioUrl.observe(this, insultAudioUrlObserver)
    }

    private fun releaseMediaPlayer() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
        } catch (e: Exception) {
            Log.d("wowbagger", "releaseMediaPlayer: could not stop or release", e)
        }
    }

    override fun onPause() {
        releaseMediaPlayer()

        super.onPause()
    }

    override fun onStop() {
        releaseMediaPlayer()

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

@Composable
fun NameSelector(name: String, onNameSelected: (String) -> Unit) {

    val isOpen = remember { mutableStateOf(false) } // initial value
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    Box {
        Column {
            OutlinedTextField(
                value = name,
                onValueChange = onNameSelected,
                label = { Text(text = stringResource(R.string.name_label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = names,
                openCloseOfDropDownList,
                onNameSelected
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable { isOpen.value = true }

        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    selectedString(it)
                }
            ) {
                Text(
                    it, modifier = Modifier
                        .wrapContentWidth()
                        .align(CenterVertically)
                )
            }
        }
    }
}

private val names = listOf(
    "",
    "Ädu",
    "Andle",
    "Ändu",
    "Ännele",
    "Annebäbi",
    "Aschi",
    "Beetli",
    "Beätu",
    "Bidu",
    "Björnu",
    "Beni",
    "Bänz",
    "Brünu",
    "Cärmle",
    "Chlöisu",
    "Chrigu",
    "Chrige",
    "Chäschpu",
    "Dänu",
    "Dävu",
    "Dölf",
    "Fäbu",
    "Fige",
    "Fippu",
    "Fixu",
    "Flöru",
    "Fränzu",
    "Fredu",
    "Fridu",
    "Fritzu",
    "Godi",
    "Gusti",
    "Gödu",
    "Hämpu",
    "Heidle",
    "Hene",
    "Housi",
    "Jänu",
    "Jasme",
    "Jole",
    "Jüre",
    "Köbu",
    "Kari",
    "Käru",
    "Keve",
    "Knütu",
    "Kusi",
    "Küsu",
    "Küre",
    "Lexu",
    "Lise",
    "Lüku",
    "Lüssle",
    "Mänu",
    "Märgu",
    "Märsu",
    "Märu",
    "Mäthu",
    "Mäuch",
    "Michu",
    "Möne",
    "Niku",
    "Nigge",
    "Nuke",
    "Ölu",
    "Pädu",
    "Pöilu",
    "Pesche",
    "Räffu",
    "Rebe",
    "Res",
    "Retölu",
    "Richu",
    "Role",
    "Rölu",
    "Röschu",
    "Röifu",
    "Römu",
    "Rüfe",
    "Rüedu",
    "Sabe",
    "Sämu",
    "Sändu",
    "Särele",
    "Säschu",
    "Schöggu",
    "Sebu",
    "Sile",
    "Simu",
    "Sime",
    "Söne",
    "Steffu",
    "Stifu",
    "Susle",
    "Seppu",
    "Schämpu",
    "Schane",
    "Stöffu",
    "Tesi",
    "Tesle",
    "Tinu",
    "Trixle",
    "Trudle",
    "Tömu",
    "Tönu",
    "Üelu",
    "Ürsu",
    "Wale",
    "Wäutu",
    "Wernu",
    "Werni",
    "Vane",
    "Vidu",
    "Vrene",
)

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