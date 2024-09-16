package com.treefrogapps.androidx.compose.material3.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class ExtendedTypography internal constructor(
    val small: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    val smallBold: TextStyle = small.copy(
        fontWeight = FontWeight.Bold
    ),
    val medium: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.25.sp
    ),
    val mediumBold: TextStyle = medium.copy(
        fontWeight = FontWeight.Bold
    ),
    val large: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    val largeBold: TextStyle = large.copy(
        fontWeight = FontWeight.Bold
    ),
)

class ExtendedTypographyColors internal constructor(
    primary: Color,
    primaryInverse: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    secondaryInverse: Color,
    error: Color
) {
    var primary by mutableStateOf(primary)
        private set
    var primaryInverse by mutableStateOf(primaryInverse)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var secondaryVariant by mutableStateOf(secondaryVariant)
        private set
    var secondaryInverse by mutableStateOf(secondaryInverse)
        private set
    var error by mutableStateOf(error)
        private set

    fun copy(): ExtendedTypographyColors = ExtendedTypographyColors(
        primary = this.primary,
        primaryInverse = this.primaryInverse,
        primaryVariant = this.primaryVariant,
        secondary = this.secondary,
        secondaryVariant = this.secondaryVariant,
        secondaryInverse = this.secondaryInverse,
        error = this.error
    )

    fun updateColorsFrom(other: ExtendedTypographyColors) {
        primary = other.primary
        primaryInverse = other.primaryInverse
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        secondaryVariant = other.secondaryVariant
        secondaryInverse = other.secondaryInverse
        error = other.error
    }
}

internal val DefaultExtendedTypography = ExtendedTypography()
internal val LocalExtendedTypography = staticCompositionLocalOf { DefaultExtendedTypography }
internal val LocalExtendedTypographyColors = staticCompositionLocalOf { lightExtendedTypographyColors() }

internal fun provideDefaultTypographyColors(darkMode: Boolean): ExtendedTypographyColors =
    if (darkMode) darkExtendedTypographyColors() else lightExtendedTypographyColors()

private fun lightExtendedTypographyColors(): ExtendedTypographyColors =
    ExtendedTypographyColors(
        primary = Color.Black,
        primaryInverse = Color.White,
        primaryVariant = Color.LightGray,
        secondary = Color.DarkGray,
        secondaryVariant = Color.Gray,
        secondaryInverse = Color.LightGray,
        error = Color.Red
    )

private fun darkExtendedTypographyColors(): ExtendedTypographyColors =
    ExtendedTypographyColors(
        primary = Color.White,
        primaryInverse = Color.Black,
        primaryVariant = Color.DarkGray,
        secondary = Color.LightGray,
        secondaryVariant = Color.Gray,
        secondaryInverse = Color.DarkGray,
        error = Color.Red
    )