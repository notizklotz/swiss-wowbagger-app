package com.github.notizklotz.swisswowbagger.app.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.notizklotz.swisswowbagger.app.R

/**
 * [Code source](https://github.com/androidx/androidx/blob/androidx-main/compose/material/material/samples/src/main/java/androidx/compose/material/samples/ExposedDropdownMenuSamples.kt)
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InsultTargetNameSelector(name: String, onNameChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(name) }
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = if (selectedOptionText.isNotBlank()) !expanded else expanded
        }
    ) {
        TextField(
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
                expanded = true
                onNameChange(selectedOptionText)
            },
            label = { Text(stringResource(R.string.name_label)) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.testTag(TEST_TAG_INSULT_TARGET_NAME).fillMaxWidth(),
            singleLine = true,
        )

        val filteringOptions =
            names.filter {
                it.contains(selectedOptionText, ignoreCase = true)
            }

        if (filteringOptions.isNotEmpty()) {
            val focusManager = LocalFocusManager.current

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                LazyColumn(modifier = Modifier.height(300.dp).width(200.dp)) {
                    items(items = filteringOptions, key = { it }) {
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = it
                                expanded = false
                                focusManager.clearFocus()
                                onNameChange(it)
                            }
                        ) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}

const val TEST_TAG_INSULT_TARGET_NAME = "InsultTargetName"

internal val names = listOf(
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