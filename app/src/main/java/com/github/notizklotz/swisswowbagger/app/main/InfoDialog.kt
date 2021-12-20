package com.github.notizklotz.swisswowbagger.app.main

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.notizklotz.swisswowbagger.app.R

@Composable
fun InfoDialog(onDismissRequest: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            InfoDialogContent()
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onDismissRequest
                ) {
                    Text("Ha gnue gseh")
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun InfoDialogContent() {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        item {
            Text(text = "Beteiligti Söiniggle:", style = MaterialTheme.typography.h6)
        }

        item {
            InfoDialogCard(
                drawableId = R.drawable.nidi,
                contentDescription = "Büud vom Nidi",
                name = "Nidi",
                description = "Beleidigungsgenerator und Sprachsynthese (sSchwirige)",
                link = Uri.parse("http://nidi.guru")
            )
        }

        item {
            InfoDialogCard(
                drawableId = R.drawable.aedu,
                contentDescription = "Büud vom Ädu",
                name = "Ädu",
                description = "Programmierig vor App (sEifache)",
                link = Uri.parse("https://github.com/notizklotz")
            )
        }


        item { Spacer(modifier = Modifier.height(6.dp))}
        item {
            Text(text = "Üs chame miete bir:", style = MaterialTheme.typography.h6)
        }

        item {
            val context = LocalContext.current
            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.schaltstelle.ch")) }

            Card(onClick = { context.startActivity(intent) }, shape = RectangleShape) {
                Image(
                    painter = painterResource(id = R.drawable.schaltelle),
                    contentDescription = "Logo vor Firma Schaltstelle",
                    Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                )
            }
        }


        item { Spacer(modifier = Modifier.height(6.dp))}
        item {
            Text(text = "Links:", style = MaterialTheme.typography.h6)
        }

        item {
            GithubLinkButton("Nidi's Original im Web", Uri.parse("https://nidi3.github.io/swiss-wowbagger"))
            GithubLinkButton("App Sourcecode", Uri.parse("https://github.com/notizklotz/swiss-wowbagger-app"))
            GithubLinkButton("Wowbagger Sourcecode", Uri.parse("https://github.com/nidi3/swiss-wowbagger"))
        }
    }
}

@Composable
private fun GithubLinkButton(text: String, uri: Uri) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, uri) }

    Button(
        onClick = { context.startActivity(intent) },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_mark_github),
            contentDescription = "Github Logo",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfoDialogCard(
    drawableId: Int,
    contentDescription: String,
    name: String,
    description: String,
    link: Uri
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, link) }

    Card(onClick = { context.startActivity(intent) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = contentDescription
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = name, style = MaterialTheme.typography.h6)
                Text(text = description)
            }
        }
    }
}

@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun InfoDialogPreview() {
    InfoDialogContent()
}