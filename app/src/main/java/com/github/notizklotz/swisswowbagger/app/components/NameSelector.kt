package com.github.notizklotz.swisswowbagger.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.notizklotz.swisswowbagger.app.R

@Composable
fun NameSelector(preselectedName: String, onNameSelected: (String) -> Unit) {

    val isOpen = remember { mutableStateOf(false) }

    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    Box {
        Column {
            OutlinedTextField(
                value = preselectedName,
                onValueChange = {},
                label = { Text(text = stringResource(R.string.name_label)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            DropDownList(
                requestToOpen = isOpen.value,
                list = names,
                request = openCloseOfDropDownList,
                onEntrySelected = onNameSelected
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable { isOpen.value = true }

        )
    }
}

@Composable
fun DropDownList(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    onEntrySelected: (String) -> Unit
) {
    DropdownMenu(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp),
        expanded = requestToOpen,
        onDismissRequest = { request(false) },
    ) {
        list.forEach {
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    request(false)
                    onEntrySelected(it)
                }
            ) {
                Text(
                    it, modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

private val names = listOf(
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