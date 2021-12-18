package com.github.notizklotz.swisswowbagger.app.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.notizklotz.swisswowbagger.app.R
import com.github.notizklotz.swisswowbagger.app.ui.theme.Red
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme
import com.github.notizklotz.swisswowbagger.app.ui.theme.Yellow

/**
 * Height of the FAB overlapping the bottom sheet.
 * := Half of [FloatingActionButton].ExtendedFabSize
 */
private val fabHeightInSheet = 24.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScaffold(
    insult: String,
    name: String,
    onInsultClicked: () -> Unit,
    onNameChange: (String) -> Unit
) {
    SwissWowbaggerAppTheme {
        BottomSheetScaffold(
            scaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = rememberBottomSheetState(
                    BottomSheetValue.Expanded
                )
            ),
            floatingActionButton = {
                InsultFloatingActionButton(onInsultClicked)
            },
            floatingActionButtonPosition = FabPosition.Center,
            topBar = {
                MainAppBar()
            },
            sheetContent = {
                MainUserInput(
                    name,
                    Modifier
                        .padding(top = fabHeightInSheet)
                        .padding(8.dp),
                    onNameChange
                )
            }
        ) { padding ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(padding)
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                InsultText(insult)
            }
        }
    }
}

@Composable
private fun MainUserInput(
    name: String,
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        InsultTargetNameSelector(name) {
            onNameChange(it)
        }
    }
}

@Composable
private fun MainAppBar() {
    val openDialog = remember { mutableStateOf(false) }

    TopAppBar {
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { openDialog.value = true }) {
            Icon(Icons.Filled.Info, contentDescription = "Info")
        }
    }

    if (openDialog.value) {
        InfoDialog { openDialog.value = false }
    }
}

@Composable
private fun InsultFloatingActionButton(onClick: () -> Unit) {

    ExtendedFloatingActionButton(
        onClick = { onClick() },
        text = { Text(stringResource(R.string.insult_button_text)) },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_bubble_24),
                contentDescription = null // decorative element
            )
        }
    )
}

@Composable
private fun InsultText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = if (MaterialTheme.colors.isLight) Red else Yellow,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = modifier
            .fillMaxWidth()
            .testTag(TEST_TAG_INSULT_TEXT)
    )
}

const val TEST_TAG_INSULT_TEXT = "InsultText"

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DefaultPreview() {
    MainScaffold(insult = "Nüt", name = "", onInsultClicked = { }, onNameChange = {})
}