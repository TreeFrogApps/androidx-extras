package com.treefrogapps.androidx.compose.activity

import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.delay

/**
 * Composable that will use [LocalWindow] and call [Window.setStatusBarColor].
 *
 * This will only occur if the [LocalWindow] is not null
 */
@Composable
fun SystemStatusBarColorEffect(
    color: Color,
    animateBetweenColors: Boolean = true
) {
    WindowColorEffect(
        color = color,
        animateBetweenColors = animateBetweenColors,
        onEach = { targetColor -> statusBarColor = targetColor },
        onCompleted = { colorUpdated -> statusBarColor = colorUpdated })
}

/**
 * Composable that will use [LocalWindow] and call [Window.setNavigationBarColor].
 *
 * This will only occur if the [LocalWindow] is not null
 */
@Composable
fun SystemNavigationBarColorEffect(
    color: Color,
    animateBetweenColors: Boolean = true
) {
    WindowColorEffect(
        color = color,
        animateBetweenColors = animateBetweenColors,
        onEach = { targetColor -> navigationBarColor = targetColor },
        onCompleted = { colorUpdated -> navigationBarColor = colorUpdated })
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
    navigationBarColor: Color,
    animateBetweenColors: Boolean = true
) {
    SystemStatusBarColorEffect(
        color = statusBarColor,
        animateBetweenColors = animateBetweenColors
    )
    SystemNavigationBarColorEffect(
        color = navigationBarColor,
        animateBetweenColors = animateBetweenColors
    )
}

@Composable
private fun WindowColorEffect(
    color: Color,
    animateBetweenColors: Boolean = true,
    onEach: Window.(targetColor: Int) -> Unit,
    onCompleted: Window.(updatedColor: Int) -> Unit
) {
    val window = LocalWindow.current
    val colorUpdated by rememberUpdatedState(newValue = color)
    var colorSaved by rememberSaveable { mutableStateOf<Int?>(value = null) }

    LaunchedEffect(
        key1 = colorUpdated
    ) {
        window?.run {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (colorSaved != colorUpdated.toArgb()) {
                colorSaved = colorUpdated.toArgb()
                if (animateBetweenColors) {
                    animateColor(
                        start = Color(color = statusBarColor),
                        stop = colorUpdated,
                        onEach = { onEach(window, it) })
                }
            }
            onCompleted(window, colorUpdated.toArgb())
        }
    }
}

private suspend inline fun animateColor(
    start: Color,
    stop: Color,
    onEach: (targetColor: Int) -> Unit
) {
    (1..100 / 4).forEach { idx ->
        val fraction: Float = (idx * 4) / 100f
        val targetColor = lerp(start = start, stop = stop, fraction = fraction).toArgb()
        onEach(targetColor)
        delay(timeMillis = 20)
    }
}