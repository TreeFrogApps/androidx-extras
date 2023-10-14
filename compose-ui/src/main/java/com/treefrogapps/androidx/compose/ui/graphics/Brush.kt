package com.treefrogapps.androidx.compose.ui.graphics

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
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

internal const val defaultDurationMillis: Int = 1600
internal val defaultShimmerColors: List<Color>
    @Composable get() = if (isSystemInDarkTheme()) darkShimmerColors else lightShimmerColors

@Stable
fun Brush.Companion.linearVerticalGradient(
    start: Color,
    end: Color,
    tileMode: TileMode = TileMode.Clamp,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = listOf(start, end),
    stops = null,
    tileMode = tileMode,
    angleInDegrees = 270.0f,
    useAsCssAngle = useAsCssAngle
)

@Stable
fun Brush.Companion.linearHorizontalGradient(
    start: Color,
    end: Color,
    tileMode: TileMode = TileMode.Clamp,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = listOf(start, end),
    stops = null,
    tileMode = tileMode,
    angleInDegrees = 0.0f,
    useAsCssAngle = useAsCssAngle
)

/**
 * Creates a linear gradient with the provided colors
 * and angle.
 *
 * @param colors Colors of gradient
 * @param stops Offsets to determine how the colors are dispersed throughout
 * the vertical gradient
 * @param tileMode Determines the behavior for how the shader is to fill a region outside
 * its bounds. Defaults to [TileMode.Clamp] to repeat the edge pixels
 * @param angleInDegrees Angle of a gradient in degrees
 * @param useAsCssAngle Determines whether the CSS gradient angle should be used
 * by default cartesian angle is used
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient">
 *     linear-gradient</a>
 */
@Stable
fun Brush.Companion.linearGradient(
    colors: List<Color>,
    stops: List<Float>? = null,
    tileMode: TileMode = TileMode.Clamp,
    angleInDegrees: Float = 0f,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = colors,
    stops = stops,
    tileMode = tileMode,
    angleInDegrees = angleInDegrees,
    useAsCssAngle = useAsCssAngle
)

/**
 * Creates a linear gradient with the provided colors
 * and angle.
 *
 * @param colorStops Colors and their offset in the gradient area
 * @param tileMode Determines the behavior for how the shader is to fill a region outside
 * its bounds. Defaults to [TileMode.Clamp] to repeat the edge pixels
 * @param angleInDegrees Angle of a gradient in degrees
 * @param useAsCssAngle Determines whether the CSS gradient angle should be used
 * by default cartesian angle is used
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient">
 *     linear-gradient</a>
 */
@Stable
fun Brush.Companion.linearGradient(
    vararg colorStops: Pair<Float, Color>,
    tileMode: TileMode = TileMode.Clamp,
    angleInDegrees: Float = 0f,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = List(colorStops.size) { i -> colorStops[i].second },
    stops = List(colorStops.size) { i -> colorStops[i].first },
    tileMode = tileMode,
    angleInDegrees = angleInDegrees,
    useAsCssAngle = useAsCssAngle
)

@Composable
fun shimmerBrush(
    enabled: Boolean = true,
    shimmerColors: List<Color> = defaultShimmerColors,
    durationMillis: Int = defaultDurationMillis
): Brush = if (enabled) {
    val start = 0.0f
    val gradientWidth = 400.0f
    val transition = rememberInfiniteTransition(label = "ShimmerInfiniteTransition")
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis),
            repeatMode = RepeatMode.Restart
        ),
        label = "ShimmerFloatAnimation"
    )
    val startXYOffset = max(start, translateAnimation - gradientWidth)
    val endXYOffset = translateAnimation
    Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = startXYOffset, y = startXYOffset),
        end = Offset(x = endXYOffset, y = endXYOffset)
    )
} else {
    Brush.linearGradient(
        colors = defaultColors,
        start = Offset.Zero,
        end = Offset.Zero
    )
}