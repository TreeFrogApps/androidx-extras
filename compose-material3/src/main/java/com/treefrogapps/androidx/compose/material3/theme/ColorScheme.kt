package com.treefrogapps.androidx.compose.material3.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Extended Colors
 */
object ExtendedColors {
    val BlackTint05 = Color(color = 0x0C000000)
    val BlackTint10 = Color(color = 0x19000000)
    val BlackTint15 = Color(color = 0x26000000)
    val BlackTint20 = Color(color = 0x33000000)
    val BlackTint30 = Color(color = 0x4D000000)
    val BlackTint40 = Color(color = 0x66000000)
    val BlackTint50 = Color(color = 0x80000000)
    val BlackTint60 = Color(color = 0x99000000)
    val WhiteTint05 = Color(color = 0x0CFFFFFF)
    val WhiteTint10 = Color(color = 0x19FFFFFF)
    val WhiteTint15 = Color(color = 0x26FFFFFF)
    val WhiteTint20 = Color(color = 0x33FFFFFF)
    val WhiteTint30 = Color(color = 0x4DFFFFFF)
    val WhiteTint40 = Color(color = 0x66FFFFFF)
    val WhiteTint50 = Color(color = 0x80FFFFFF)
    val WhiteTint60 = Color(color = 0x99FFFFFF)
}

fun provideDefaultColors(darkMode: Boolean): ColorScheme = if (darkMode) darkColorScheme() else lightColorScheme()