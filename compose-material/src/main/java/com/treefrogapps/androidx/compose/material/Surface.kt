package com.treefrogapps.androidx.compose.material

import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Calculate the surface color at [elevation].
 *
 * This will return the elevated surface color if [LocalElevationOverlay] is not null, otherwise
 * this wil return the normal [MaterialTheme.colors] surface color.
 */
@Composable
fun surfaceColorAtElevation(
    elevation: Dp = 0.dp
): Color =
    LocalElevationOverlay.current
        ?.takeIf { MaterialTheme.colors.primarySurface == MaterialTheme.colors.surface }
        ?.apply(
            color = MaterialTheme.colors.surface,
            elevation = LocalAbsoluteElevation.current + elevation
        ) ?: MaterialTheme.colors.primarySurface

/**
 * Calculate the color at [elevation].
 *
 * This will return the elevated [color] if [LocalElevationOverlay] is not null, otherwise
 * this wil return [color].
 */
@Composable
fun colorAtElevation(
    color: Color,
    elevation: Dp = 0.dp
): Color =
    LocalElevationOverlay.current
        ?.apply(
            color = color,
            elevation = LocalAbsoluteElevation.current + elevation
        ) ?: color