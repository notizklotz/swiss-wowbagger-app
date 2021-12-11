package com.github.notizklotz.swisswowbagger.app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

/**
 * [Code source](https://github.com/androidx/androidx/blob/androidx-main/compose/material/material/samples/src/main/java/androidx/compose/material/samples/ExposedDropdownMenuSamples.kt)
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InsultTargetNameSelector(preselectedName: String, onNameSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(preselectedName) }
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
            },
            label = { Text("Wär?") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
                filteringOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                            focusManager.clearFocus()
                            onNameSelected(selectionOption)
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
    }
}

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