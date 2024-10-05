package com.treefrogapps.androidx.compose.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable


abstract class EdgeToEdgeComposeComponentActivity : ComponentActivity() {

    abstract val content: @Composable () -> Unit

    open val statusBarStyle: SystemBarStyle =
        SystemBarStyle.auto(
            lightScrim = Color.TRANSPARENT,
            darkScrim = Color.TRANSPARENT
        )
    open val navigationBarStyle: SystemBarStyle =
        SystemBarStyle.auto(
            lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF),
            darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle
        )
        setContent(content = content)
    }
}
