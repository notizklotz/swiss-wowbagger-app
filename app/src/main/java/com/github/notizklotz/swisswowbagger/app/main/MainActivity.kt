package com.github.notizklotz.swisswowbagger.app.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.github.notizklotz.swisswowbagger.app.data.Insult
import com.github.notizklotz.swisswowbagger.app.data.Voice

class MainActivity : ComponentActivity() {

    @VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            val insult: Insult? by viewModel.insult.observeAsState()
            val name: String by viewModel.name.observeAsState("")
            val voice: Voice by viewModel.voice.observeAsState(Voice.exilzuerchere)
            val loading: Boolean by viewModel.loading.observeAsState(false)
            val error: Boolean by viewModel.error.observeAsState(false)

            val scaffoldState: ScaffoldState = rememberScaffoldState()
            if (error) {

                // `LaunchedEffect` will cancel and re-launch if
                // `scaffoldState.snackbarHostState` changes
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    // Show snackbar using a coroutine, when the coroutine is cancelled the
                    // snackbar will automatically dismiss. This coroutine will cancel whenever
                    // `state.hasError` is false, and only start when `state.hasError` is true
                    // (due to the above if-check), or if `scaffoldState.snackbarHostState` changes.
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = "W himutruurige Fähler isch passiert",
                        actionLabel = "Nomou"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.fetchInsultAndPlay(viewModel.insult.value?.id.toString())
                    }
                }
            }

            MainScaffold(
                scaffoldState = scaffoldState,
                insult = insult,
                name = name,
                voice = voice,
                loading,
                onInsultClicked = {
                    viewModel.fetchInsultAndPlay()
                },
                onNameChange = {
                    viewModel.name.value = it
                },
                onVoiceChange = {
                    viewModel.setVoiceAndPlay(it)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val wowbaggerUri: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            if (wowbaggerUri != null) {
                val insultId = wowbaggerUri.fragment
                val name = wowbaggerUri.getQueryParameter("names")
                val voice = wowbaggerUri.getQueryParameter("voice")?.let { Voice.valueOf(it) }
                viewModel.setValuesAndPlay(insultId, name, voice)
            }
        }
    }
}
