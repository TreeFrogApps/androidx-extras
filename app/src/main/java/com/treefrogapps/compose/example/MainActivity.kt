package com.treefrogapps.compose.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.example.gestures.GesturesActivity
import com.treefrogapps.compose.example.parallax.ParallaxActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }

    @Composable
    private fun MainActivityContent() {
        MaterialTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ExampleButton<ParallaxActivity>(text = "Parallax Example")
                    ExampleButton<GesturesActivity>(text = "Gestures Example")
                }
            }
        }
    }

    @Composable
    private inline fun <reified T : Activity> ExampleButton(text: String) {
        val context = LocalContext.current

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            onClick = { context.startActivity(Intent(context, T::class.java)) }
        ) {
            Text(text = text)
        }
    }

}

