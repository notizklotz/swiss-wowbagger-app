package com.github.notizklotz.swisswowbagger.app

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme


class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userData = viewModel.insult.observeAsState("Ganz brav")

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

            SwissWowbaggerAppTheme {

                Scaffold(
                    floatingActionButton = { InsultButton { viewModel.fetchInsult() } },
                    floatingActionButtonPosition = FabPosition.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        InsultText(userData.value)
                    }
                }
            }
        }
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
        text = { Text("Beleidige!") },
        icon = { Icon(Icons.Filled.Favorite, "") }
    )
}

@Composable
fun InsultText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Center,
        fontSize = 30.sp
    )
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DefaultPreview() {
    SwissWowbaggerAppTheme {
        Column {
            InsultButton { }
            InsultText("nö")
        }
    }
}