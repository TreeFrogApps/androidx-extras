package com.treefrogapps.androidx.compose.ui.graphics

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode


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
    useAsCssAngle = useAsCssAngle)

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
    useAsCssAngle = useAsCssAngle)

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
    useAsCssAngle = useAsCssAngle)

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
    useAsCssAngle = useAsCssAngle)