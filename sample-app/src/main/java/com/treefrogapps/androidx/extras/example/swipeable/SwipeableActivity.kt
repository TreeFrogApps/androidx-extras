package com.treefrogapps.androidx.extras.example.swipeable

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.treefrogapps.androidx.compose.material.swipeable.ConfirmationSwipeable
import com.treefrogapps.androidx.compose.ui.tooling.preview.DayNightPreview

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
        MaterialTheme(
            colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
        ) {
            Surface {
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
    @DayNightPreview
    private fun GesturesActivityPreview() {
        GesturesActivityContent()
    }
}

