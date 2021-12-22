package com.github.notizklotz.swisswowbagger.app.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.github.notizklotz.swisswowbagger.app.data.Voice

const val TEST_TAG_VOICE = "Voice"

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VoiceSelector(voice: Voice, onVoiceChange: (Voice) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(voice) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedOption.label,
            onValueChange = { },
            label = { Text("Stimm") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.testTag(TEST_TAG_VOICE).fillMaxWidth(),
            singleLine = true,
            readOnly = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            Voice.values().forEach {
                DropdownMenuItem(
                    onClick = {
                        selectedOption = it
                        expanded = false
                        onVoiceChange(it)
                    }
                ) {
                    Text(text = it.label)
                }
            }
        }
    }
}