package com.github.notizklotz.swisswowbagger.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.github.notizklotz.swisswowbagger.app.InsultSpeechPlayer
import com.github.notizklotz.swisswowbagger.app.R

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

            MainScaffold(
                insult = insult.value,
                name = name.value,
                onInsultClicked = {
                    fetchInsultIdlingResource?.increment()
                    viewModel.fetchInsult()
                },
                onNameChange = {
                    viewModel.name.value = it
                }
            )
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
