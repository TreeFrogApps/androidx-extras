package com.treefrogapps.compose.material.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

internal val DefaultShapes
    @ReadOnlyComposable
    @Composable get() = Shapes(
        small = RoundedCornerShape(MaterialThemeExtended.dimens.spacing.small),
        medium = RoundedCornerShape(MaterialThemeExtended.dimens.spacing.normal),
        large = RoundedCornerShape(MaterialThemeExtended.dimens.spacing.large))