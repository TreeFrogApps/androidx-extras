package com.treefrogapps.compose.example.swipeable

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.material.swipeable.ConfirmationSwipeable

class SwipeableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GesturesActivityContent()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun GesturesActivityContent() {
        MaterialTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp)
            ) {
                Column {
                    ConfirmationSwipeable {
                        Log.e("Example", "Confirmed")
                    }
                    ConfirmationSwipeable {
                        Log.e("Example", "Confirmed")
                    }
                    ConfirmationSwipeable {
                        Log.e("Example", "Confirmed")
                    }
                    ConfirmationSwipeable {
                        Log.e("Example", "Confirmed")
                    }
                    ConfirmationSwipeable {
                        Log.e("Example", "Confirmed")
                    }
                }
            }
        }
    }

    @Composable
    @Preview(
        showSystemUi = true,
        showBackground = true,
        device = Devices.PIXEL_4
    )
    private fun GesturesActivityPreview() {
        GesturesActivityContent()
    }
}

