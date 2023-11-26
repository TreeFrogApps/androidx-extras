package com.treefrogapps.androidx.compose.material

import androidx.compose.material.ElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
private fun surfaceColorAtElevation(
    color: Color,
    elevationOverlay: ElevationOverlay?,
    absoluteElevation: Dp = 0.dp
): Color =
    elevationOverlay
        ?.takeIf { color == MaterialTheme.colors.surface }
        ?.apply(color = color, absoluteElevation)
        ?: color