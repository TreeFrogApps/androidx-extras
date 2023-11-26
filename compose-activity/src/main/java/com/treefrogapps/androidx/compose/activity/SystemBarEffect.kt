package com.treefrogapps.androidx.compose.activity

import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Composable that will use [LocalWindow] and call [Window.setStatusBarColor].
 *
 * This will only occur if the [LocalWindow] is not null
 */
@Composable
fun SystemStatusBarColorEffect(
    color: Color
) {
    val window = LocalWindow.current
    val colorUpdated by rememberUpdatedState(newValue = color)

    LaunchedEffect(key1 = colorUpdated) {
        window?.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = colorUpdated.toArgb()
        }
    }
}

/**
 * Composable that will use [LocalWindow] and call [Window.setNavigationBarColor].
 *
 * This will only occur if the [LocalWindow] is not null
 */
@Composable
fun SystemNavigationBarColorEffect(
    color: Color
) {
    val window = LocalWindow.current
    val colorUpdated by rememberUpdatedState(newValue = color)

    LaunchedEffect(key1 = colorUpdated) {
        window?.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            navigationBarColor = colorUpdated.toArgb()
        }
    }
}


/**
 * Composable that will use [LocalWindow] and call [Window.setStatusBarColor]
 * and [Window.setNavigationBarColor].
 *
 * This will only occur if the [LocalWindow] is not null
 */
@Composable
fun SystemBarsColorEffect(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val window = LocalWindow.current
    val statusBarColorUpdated by rememberUpdatedState(newValue = statusBarColor)
    val navigationBarColorUpdated by rememberUpdatedState(newValue = navigationBarColor)

    LaunchedEffect(
        key1 = statusBarColorUpdated,
        key2 = navigationBarColorUpdated
    ) {
        window?.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            setStatusBarColor(statusBarColorUpdated.toArgb())
            setNavigationBarColor(navigationBarColorUpdated.toArgb())
        }
    }
}