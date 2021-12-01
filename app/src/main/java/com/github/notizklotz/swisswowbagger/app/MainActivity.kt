package com.github.notizklotz.swisswowbagger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.notizklotz.swisswowbagger.app.ui.theme.SwissWowbaggerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwissWowbaggerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    InsultButton()
                }
            }
        }
    }
}

@Composable
fun InsultButton() {
    Button(onClick = { /*TODO*/ }) {
        Text("Beleidige!")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwissWowbaggerAppTheme {
        InsultButton()
    }
}