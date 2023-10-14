package com.treefrogapps.androidx.compose.ui.graphics

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.math.max


private val lightShimmerColors = listOf(
    Color(color = 0xFFE7E7E7),
    Color(color = 0xFFF7F7F7),
    Color(color = 0xFFE7E7E7)
)
private val darkShimmerColors = listOf(
    Color(color = 0xFF292929),
    Color(color = 0xFF383838),
    Color(color = 0xFF292929)
)
private val defaultColors = listOf(
    Color.Transparent,
    Color.Transparent
)
private const val defaultDurationMillis: Int = 1600

val LocalShimmerTheme: ProvidableCompositionLocal<ShimmerTheme> =
    staticCompositionLocalOf { DefaultShimmerTheme() }

abstract class ShimmerTheme {

    @Composable
    abstract fun colors(): List<Color>

    open fun durationMillis(): Int = defaultDurationMillis

    @Composable
    open fun animation(): State<Float> {
        val transition = rememberInfiniteTransition(label = "ShimmerInfiniteTransition")
        return transition.animateFloat(
            initialValue = 0f,
            targetValue = 1200f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = durationMillis()),
                repeatMode = RepeatMode.Restart
            ),
            label = "ShimmerFloatAnimation"
        )
    }
}

internal class DefaultShimmerTheme : ShimmerTheme() {
    @Composable
    override fun colors(): List<Color> =
        if (isSystemInDarkTheme()) darkShimmerColors else lightShimmerColors
}

@Composable
fun shimmerBrush(
    enabled: Boolean = true,
    shimmerTheme: ShimmerTheme = LocalShimmerTheme.current
): Brush = if (enabled) {
    val start = 0.0f
    val gradientWidth = 400.0f
    val translateAnimation = shimmerTheme.animation().value
    val startXYOffset = max(start, translateAnimation - gradientWidth)
    Brush.linearGradient(
        colors = shimmerTheme.colors(),
        start = Offset(x = startXYOffset, y = startXYOffset),
        end = Offset(x = translateAnimation, y = translateAnimation)
    )
} else {
    Brush.linearGradient(
        colors = defaultColors,
        start = Offset.Zero,
        end = Offset.Zero
    )
}


