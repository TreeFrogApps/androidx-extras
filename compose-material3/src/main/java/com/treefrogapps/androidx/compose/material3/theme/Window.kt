package com.treefrogapps.androidx.compose.material3.theme

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
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.LargeLandscape
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.LargePortrait
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.MediumLandscape
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.MediumPortrait
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.SmallLandscape
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type.SmallPortrait

/**
 * Window size class for use in compose for determining the screen bucket size
 */
data class WindowSize(
    val type: Type,
    val size: DpSize
) {
    val name = type.name

    enum class Type {
        SmallPortrait,
        SmallLandscape,
        MediumPortrait,
        MediumLandscape,
        LargePortrait,
        LargeLandscape;
    }

    companion object {

        fun WindowSize.isSmall(): Boolean =
            type === SmallPortrait || type === SmallLandscape

        fun WindowSize.isMedium(): Boolean =
            type === MediumPortrait || type === MediumLandscape

        fun WindowSize.isLarge(): Boolean =
            type === LargePortrait || type === LargeLandscape

        fun WindowSize.isLandscape(): Boolean =
            type === SmallLandscape || type === MediumLandscape || type === LargeLandscape
    }
}

fun windowSizeOf(
    type: WindowSize.Type = WindowSize.Type.SmallPortrait,
    dimens: DpSize = DpSize.Unspecified
): WindowSize = WindowSize(type, dimens)

internal val LocalWindowSize = staticCompositionLocalOf {
    WindowSize(
        type = SmallPortrait,
        size = DpSize.Unspecified
    )
}

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
            width < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            width <= 460.dp -> SmallPortrait
            width <= 640.dp -> MediumPortrait
            else -> LargePortrait
        }.let { type -> WindowSize(type = type, size = this) }
    } else {
        when {
            width < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
            width <= 960.dp -> SmallLandscape
            width <= 1024.dp -> MediumLandscape
            else -> LargeLandscape
        }.let { type -> WindowSize(type = type, size = this) }
    }
