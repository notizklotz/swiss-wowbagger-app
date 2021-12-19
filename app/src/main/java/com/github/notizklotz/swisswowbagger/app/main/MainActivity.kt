package com.github.notizklotz.swisswowbagger.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.data.Insult

class MainActivity : ComponentActivity() {

    @VisibleForTesting
    internal val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val insult: Insult? by viewModel.insult.observeAsState()
            val name: String by viewModel.name.observeAsState("")

            MainScaffold(
                insultText = insult?.text ?: getString(R.string.insult_initial),
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
}
