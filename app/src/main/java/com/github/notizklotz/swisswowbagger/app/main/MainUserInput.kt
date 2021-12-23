package com.github.notizklotz.swisswowbagger.app.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.notizklotz.swisswowbagger.app.data.Voice

@Composable
fun MainUserInput(
    name: String,
    voice: Voice,
    loading: Boolean,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onVoiceChange: (Voice) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        InsultTargetNameSelector(name) {
            onNameChange(it)
        }
        Spacer(modifier = Modifier.height(4.dp))
        VoiceSelector(voice, onVoiceChange)
    }
}