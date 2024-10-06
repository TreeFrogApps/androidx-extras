package com.treefrogapps.androidx.compose.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable


abstract class EdgeToEdgeComposeComponentActivity : ComponentActivity() {

    protected abstract val content: @Composable () -> Unit
    protected open val statusBarStyle: SystemBarStyle
        get() =
            SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
    protected open val navigationBarStyle: SystemBarStyle
        get() =
            SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle
        )
        super.onCreate(savedInstanceState)
        setContent(content = content)
    }
}
