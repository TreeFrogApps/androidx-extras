package com.treefrogapps.compose.parallax.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

internal val DefaultShapes
    @ReadOnlyComposable
    @Composable get() = Shapes(
        small = RoundedCornerShape(AppTheme.dimens.spacing.small),
        medium = RoundedCornerShape(AppTheme.dimens.spacing.normal),
        large = RoundedCornerShape(AppTheme.dimens.spacing.large))