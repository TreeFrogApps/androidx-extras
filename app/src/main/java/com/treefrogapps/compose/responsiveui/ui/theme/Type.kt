package com.treefrogapps.compose.responsiveui.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class AppTypography internal constructor(
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

class AppTypographyColors internal constructor(
    primary: Color,
    primaryInverse: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryInverse: Color,
    accent: Color,
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
    var secondaryInverse by mutableStateOf(secondaryInverse)
        private set
    var accent by mutableStateOf(accent)
        private set
    var error by mutableStateOf(error)
        private set

    fun copy(): AppTypographyColors = AppTypographyColors(
        primary = this.primary,
        primaryInverse = this.primaryInverse,
        primaryVariant = this.primaryVariant,
        secondary = this.secondary,
        secondaryInverse = this.secondaryInverse,
        accent = this.accent,
        error = this.error)

    fun updateColorsFrom(other: AppTypographyColors) {
        primary = other.primary
        primaryInverse = other.primaryInverse
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        secondaryInverse = other.secondaryInverse
        accent = other.accent
        error = other.error
    }
}

internal val DefaultAppTypography = AppTypography()

internal val LocalAppTypography = staticCompositionLocalOf { DefaultAppTypography }
internal val LocalAppTypographyColors = staticCompositionLocalOf { lightTypographyColors() }

internal fun provideDefaultTypographyColors(darkMode: Boolean): AppTypographyColors = if (darkMode) darkTypographyColors() else lightTypographyColors()

private fun lightTypographyColors(): AppTypographyColors =
    AppTypographyColors(
        primary = Color.Black,
        primaryInverse = Color.White,
        primaryVariant = AppColors.Light.BlueGrayDark,
        secondary = Color.DarkGray,
        secondaryInverse = Color.LightGray,
        accent = AppColors.Light.Blue,
        error = Color.Red)

private fun darkTypographyColors(): AppTypographyColors =
    AppTypographyColors(
        primary = Color.White,
        primaryInverse = Color.Black,
        primaryVariant = AppColors.Dark.BlueGrayDark,
        secondary = Color.LightGray,
        secondaryInverse = Color.DarkGray,
        accent = AppColors.Dark.Blue,
        error = Color.Red)