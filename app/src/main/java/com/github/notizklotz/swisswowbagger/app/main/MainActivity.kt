package com.github.notizklotz.swisswowbagger.app.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.github.notizklotz.swisswowbagger.app.data.Insult

class MainActivity : ComponentActivity() {

    @VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            val insult: Insult? by viewModel.insult.observeAsState()
            val name: String by viewModel.name.observeAsState("")

            MainScaffold(
                insult = insult,
                name = name,
                onInsultClicked = {
                    viewModel.fetchInsult()
                },
                onNameChange = {
                    viewModel.name.value = it
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
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction) {
            appLinkData?.fragment?.also { fragment ->
                viewModel.fetchInsult(fragment.removePrefix("#"))
            }
        }
    }
}
