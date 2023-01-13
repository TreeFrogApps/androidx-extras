package com.treefrogapps.androidx.compose.material.theme

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toComposeRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.WindowMetrics
import androidx.window.layout.WindowMetricsCalculator

/**
 * Window size class for use in compose for determining the screen bucket size
 */
enum class WindowSize {
    SmallPortrait,
    SmallLandscape,
    MediumPortrait,
    MediumLandscape,
    LargePortrait,
    LargeLandscape;

    companion object {

        fun WindowSize.isSmall(): Boolean =
            this === SmallPortrait || this === SmallLandscape

        fun WindowSize.isMedium(): Boolean =
            this === MediumPortrait || this === MediumLandscape

        fun WindowSize.isLarge(): Boolean =
            this === LargePortrait || this === LargeLandscape

        fun WindowSize.isLandscape(): Boolean =
            this === SmallLandscape || this === MediumLandscape || this === LargeLandscape
    }
}

internal val LocalWindowSize = staticCompositionLocalOf { WindowSize.SmallPortrait }

/**
 * Remembers the [WindowSize] class for the window corresponding to the current window metrics.
 */
@Composable
fun Activity.rememberWindowSize(): WindowSize {
    val configuration: Configuration = LocalConfiguration.current
    val windowMetrics: WindowMetrics = remember(configuration) {
        WindowMetricsCalculator
            .getOrCreate()
            .computeCurrentWindowMetrics(activity = this)
    }
    return with(LocalDensity.current) {
        windowMetrics
            .bounds
            .toComposeRect()
            .size
            .toDpSize()
    }.toWindowSize()
}

private fun DpSize.toWindowSize(): WindowSize =
    if (width < height) {
        when {
            width < 0.dp    -> throw IllegalArgumentException("Dp value cannot be negative")
            width <= 460.dp -> WindowSize.SmallPortrait
            width <= 640.dp -> WindowSize.MediumPortrait
            else            -> WindowSize.LargePortrait
        }
    } else {
        when {
            width < 0.dp     -> throw IllegalArgumentException("Dp value cannot be negative")
            width <= 960.dp  -> WindowSize.SmallLandscape
            width <= 1024.dp -> WindowSize.MediumLandscape
            else             -> WindowSize.LargeLandscape
        }
    }
