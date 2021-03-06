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

    selectedOptionText = name

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
    "??du",
    "Andle",
    "??ndu",
    "??nnele",
    "Anneb??bi",
    "Aschi",
    "Beetli",
    "Be??tu",
    "Bidu",
    "Bj??rnu",
    "Beni",
    "B??nz",
    "Br??nu",
    "C??rmle",
    "Chl??isu",
    "Chrigu",
    "Chrige",
    "Ch??schpu",
    "D??nu",
    "D??vu",
    "D??lf",
    "F??bu",
    "Fige",
    "Fippu",
    "Fixu",
    "Fl??ru",
    "Fr??nzu",
    "Fredu",
    "Fridu",
    "Fritzu",
    "Godi",
    "Gusti",
    "G??du",
    "H??mpu",
    "Heidle",
    "Hene",
    "Housi",
    "J??nu",
    "Jasme",
    "Jole",
    "J??re",
    "K??bu",
    "Kari",
    "K??ru",
    "Keve",
    "Kn??tu",
    "Kusi",
    "K??su",
    "K??re",
    "Lexu",
    "Lise",
    "L??ku",
    "L??ssle",
    "M??nu",
    "M??rgu",
    "M??rsu",
    "M??ru",
    "M??thu",
    "M??uch",
    "Michu",
    "M??ne",
    "Niku",
    "Nigge",
    "Nuke",
    "??lu",
    "P??du",
    "P??ilu",
    "Pesche",
    "R??ffu",
    "Rebe",
    "Res",
    "Ret??lu",
    "Richu",
    "Role",
    "R??lu",
    "R??schu",
    "R??ifu",
    "R??mu",
    "R??fe",
    "R??edu",
    "Sabe",
    "S??mu",
    "S??ndu",
    "S??rele",
    "S??schu",
    "Sch??ggu",
    "Sebu",
    "Sile",
    "Simu",
    "Sime",
    "S??ne",
    "Steffu",
    "Stifu",
    "Susle",
    "Seppu",
    "Sch??mpu",
    "Schane",
    "St??ffu",
    "Tesi",
    "Tesle",
    "Tinu",
    "Trixle",
    "Trudle",
    "T??mu",
    "T??nu",
    "??elu",
    "??rsu",
    "Wale",
    "W??utu",
    "Wernu",
    "Werni",
    "Vane",
    "Vidu",
    "Vrene",
)