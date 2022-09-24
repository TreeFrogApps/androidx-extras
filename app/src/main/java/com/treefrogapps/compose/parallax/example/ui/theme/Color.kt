package com.treefrogapps.compose.parallax.example.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object AppColors {

    object Common {
        val Warning = Color(color = 0xFFFEC700)
        val ErrorLight = Color(color = 0xEDFF4F4F)
        val GrayNotification = Color(color = 0xFF6A6A6A)
        val GrayLight = Color(color = 0xFFCCCCCC)
        val GrayMedium = Color(color = 0xFF8A8A8A)
        val GrayDark = Color(color = 0xFF323232)
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

    object Light {
        val BlueGray = Color(color = 0xFF404b5a)
        val BlueGrayTint = Color(color = 0x1A404b5a)
        val BlueGrayDark = Color(color = 0xFF252d37)
        val Blue = Color(color = 0xFF4099de)
        val BlueTint = Color(color = 0x1A4099de)
        val BlueDark = Color(color = 0xFF2472AF)
        val BlueDarkTint = Color(color = 0x1A2472AF)
    }

    object Dark {
        val BlueGray = Color(color = 0xFF596575)
        val BlueGrayTint = Color(color = 0x1A596575)
        val BlueGrayDark = Color(color = 0xFF4B5663)
        val Blue = Color(color = 0xFF5AA3DB)
        val BlueTint = Color(color = 0x1A5AA3DB)
        val BlueDark = Color(color = 0xFF3B6B91)
        val BlueDarkTint = Color(color = 0x1A3B6B91)
    }
}

private val LightColorPalette: Colors =
    lightColors(
        primary = AppColors.Light.BlueGray,
        primaryVariant = AppColors.Light.BlueGrayDark,
        secondary = AppColors.Light.Blue,
        secondaryVariant = AppColors.Light.BlueDark
    )

private val DarkColorPalette: Colors =
    darkColors(
        primary = AppColors.Dark.BlueGray,
        primaryVariant = AppColors.Dark.BlueGrayDark,
        secondary = AppColors.Dark.Blue,
        secondaryVariant = AppColors.Dark.BlueDark
    )

internal fun provideDefaultColors(darkMode: Boolean): Colors = if (darkMode) DarkColorPalette else LightColorPalette