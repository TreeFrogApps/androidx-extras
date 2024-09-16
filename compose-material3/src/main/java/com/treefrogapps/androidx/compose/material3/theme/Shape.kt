package com.treefrogapps.androidx.compose.material3.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

internal val DefaultShapes
    @ReadOnlyComposable
    @Composable get() = Shapes(
        small = RoundedCornerShape(Theme.dimens.spacing.small),
        medium = RoundedCornerShape(Theme.dimens.spacing.normal),
        large = RoundedCornerShape(Theme.dimens.spacing.large)
    )