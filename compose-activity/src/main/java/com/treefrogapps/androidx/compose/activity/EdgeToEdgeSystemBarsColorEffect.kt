package com.treefrogapps.androidx.compose.activity

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

/**
 * Edge to edge system bars color effect that will attempt to set the provided colors using [ComponentActivity.enableEdgeToEdge]. This does all
 * the heavy lifting with [android.view.Window] and [android.view.Window.getDecorView], to set system bar styles using the provided colors.
 *
 * @param statusBarColor the [Color] for the status bar
 * @param navigationBarColor the [Color] for the navigation bar
 */
@Composable
fun EdgeToEdgeSystemBarsColorEffect(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val context = LocalContext.current
    LaunchedEffect(
        key1 = statusBarColor,
        key2 = navigationBarColor
    ) {
        (context as? ComponentActivity)?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(scrim = statusBarColor.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(scrim = navigationBarColor.toArgb())
        )
    }
}