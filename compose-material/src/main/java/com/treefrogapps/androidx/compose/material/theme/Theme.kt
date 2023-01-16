package com.treefrogapps.androidx.compose.material.theme

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

typealias Theme = MaterialThemeExtended

object MaterialThemeExtended {

    val dimens: Dimens
        @ReadOnlyComposable
        @Composable
        get() = LocalAppDimens.current

    val windowSize: WindowSize
        @ReadOnlyComposable
        @Composable
        get() = LocalWindowSize.current

    val colors: Colors
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.colors

    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.typography

    val extendedTypography: ExtendedTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalExtendedTypography.current

    val extendedTypographyColors: ExtendedTypographyColors
        @ReadOnlyComposable
        @Composable
        get() = LocalExtendedTypographyColors.current

    val shapes: Shapes
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.shapes
}

@Composable
fun MaterialThemeExtended(
    windowSize: WindowSize,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    colors: Colors = provideDefaultColors(isDarkMode),
    typography: Typography = Theme.typography,
    shapes: Shapes = DefaultShapes,
    content: @Composable () -> Unit
) {
    MaterialThemeExtended(
        windowSize = windowSize
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content)
    }
}

@Composable
private fun MaterialThemeExtended(
    windowSize: WindowSize,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    appTypography: ExtendedTypography = Theme.extendedTypography,
    extendedTypographyColors: ExtendedTypographyColors = provideDefaultTypographyColors(isDarkMode),
    content: @Composable () -> Unit
) {

    val rememberedWindowSize = remember { windowSize }
    val rememberedDimens = remember(windowSize) { windowSize.toDimens() }
    val rememberedTypographyColors = remember { extendedTypographyColors.copy() }.apply { updateColorsFrom(extendedTypographyColors) }

    CompositionLocalProvider(
        values = arrayOf(
            LocalWindowSize provides rememberedWindowSize,
            LocalAppDimens provides rememberedDimens,
            LocalExtendedTypography provides appTypography,
            LocalExtendedTypographyColors provides rememberedTypographyColors),
        content = content)
}

@Preview(
    name = "Tablet",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_C)
@Composable
fun ThemedContentPreview() {
    MaterialThemeExtended(windowSize = WindowSize.LargeLandscape) {
        PreviewContent()
    }
}

@Preview(
    name = "Tablet Dark Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C)
@Composable
fun ThemedContentDarkModePreview() {
    MaterialThemeExtended(windowSize = WindowSize.LargeLandscape) {
        PreviewContent()
    }
}

@Composable
fun PreviewContent() {
    Row(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(Theme.dimens.spacing.large)) {

            Text("Window ${Theme.windowSize.name}", color = Theme.colors.onBackground)
            Spacer(modifier = Modifier.height(Theme.dimens.spacing.normal))
            Text(text = "Dimen zero ${Theme.dimens.zero}", color = Theme.colors.onBackground)
            Text(text = "Dimen hairline ${Theme.dimens.hairline}", color = Theme.colors.onBackground)
            Text(text = "Dimen one ${Theme.dimens.one}", color = Theme.colors.onBackground)
            Text(text = "Dimen elevation ${Theme.dimens.elevation.normal}", color = Theme.colors.onBackground)
            Text(text = "Dimen elevation high ${Theme.dimens.elevation.high}", color = Theme.colors.onBackground)
            Text(text = "Dimen elevation highest ${Theme.dimens.elevation.highest}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing tiny ${Theme.dimens.spacing.tiny}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing small ${Theme.dimens.spacing.small}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing normal ${Theme.dimens.spacing.normal}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing large ${Theme.dimens.spacing.large}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing big ${Theme.dimens.spacing.big}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing massive ${Theme.dimens.spacing.massive}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing gigantic ${Theme.dimens.spacing.gigantic}", color = Theme.colors.onBackground)
            Text(text = "Dimen spacing enormous ${Theme.dimens.spacing.enormous}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon minuscule ${Theme.dimens.icon.minuscule}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon small ${Theme.dimens.icon.small}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon notification ${Theme.dimens.icon.notification}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon normal ${Theme.dimens.icon.normal}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon large ${Theme.dimens.icon.large}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon big ${Theme.dimens.icon.big}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon massive ${Theme.dimens.icon.massive}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon enormous ${Theme.dimens.icon.enormous}", color = Theme.colors.onBackground)
            Text(text = "Dimen icon thumbnail ${Theme.dimens.icon.thumbnail}", color = Theme.colors.onBackground)
        }

        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(Theme.dimens.spacing.large)) {
            Text(
                text = "Small Text Primary",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.primary)

            Text(
                text = "Small Bold Text Primary",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.primary)

            Text(
                text = "Medium Text Primary",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.primary)

            Text(
                text = "Medium Bold Text Primary",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.primary)

            Text(
                text = "Large Text Primary",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.primary)

            Text(
                text = "Large Bold Text Primary",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.primary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = Theme.colors.onBackground)) {

                Text(
                    text = "Small Text Primary Inverse",
                    style = Theme.extendedTypography.small,
                    color = Theme.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Small Bold Text Primary Inverse",
                    style = Theme.extendedTypography.smallBold,
                    color = Theme.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Medium Text Primary Inverse",
                    style = Theme.extendedTypography.medium,
                    color = Theme.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Medium Bold Text Primary Inverse",
                    style = Theme.extendedTypography.mediumBold,
                    color = Theme.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Large Text Primary Inverse",
                    style = Theme.extendedTypography.large,
                    color = Theme.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Large Bold Text Primary Inverse",
                    style = Theme.extendedTypography.largeBold,
                    color = Theme.extendedTypographyColors.primaryInverse)
            }

            Text(
                text = "Small Text Primary Variant",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Small Bold Text Primary Variant",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Medium Text Primary Variant",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Medium Bold Text Primary Variant",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Large Text Primary Variant",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Large Bold Text Primary Variant",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.primaryVariant)

            Text(
                text = "Small Text Secondary",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.secondary)

            Text(
                text = "Small Bold Text Secondary",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.secondary)

            Text(
                text = "Medium Text Secondary",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.secondary)

            Text(
                text = "Medium Bold Text Secondary",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.secondary)

            Text(
                text = "Large Text Secondary",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.secondary)

            Text(
                text = "Large Bold Text Secondary",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.secondary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = Theme.colors.onBackground)) {

                Text(
                    text = "Small Text Secondary Inverse",
                    style = Theme.extendedTypography.small,
                    color = Theme.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Small Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.smallBold,
                    color = Theme.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Text Primary Secondary Inverse",
                    style = Theme.extendedTypography.medium,
                    color = Theme.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.mediumBold,
                    color = Theme.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Large Text Secondary Inverse",
                    style = Theme.extendedTypography.large,
                    color = Theme.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Large Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.largeBold,
                    color = Theme.extendedTypographyColors.secondaryInverse)
            }
        }


        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(Theme.dimens.spacing.large)) {

            ColorSwatch(
                baseColor = Theme.colors.primary,
                name = "Theme Color Primary",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.primaryVariant,
                name = "Theme Color PrimaryVariant",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.secondary,
                name = "Theme Color Secondary",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.secondaryVariant,
                name = "Theme Color SecondaryVariant",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.background,
                name = "Theme Color Background",
                textColor = Theme.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = Theme.colors.surface,
                name = "Theme Color surface",
                textColor = Theme.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = Theme.colors.error,
                name = "Theme Color Error",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.primary,
                overlayColor = Theme.colors.onPrimary,
                name = "Theme Color OnPrimary",
                textColor = Theme.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = Theme.colors.secondary,
                overlayColor = Theme.colors.onSecondary,
                name = "Theme Color OnSecondary",
                textColor = Theme.extendedTypographyColors.secondary)

            ColorSwatch(
                baseColor = Theme.colors.background,
                overlayColor = Theme.colors.onBackground,
                name = "Theme Color OnBackground",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.surface,
                overlayColor = Theme.colors.onSurface,
                name = "Theme Color OnSurface",
                textColor = Theme.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = Theme.colors.error,
                overlayColor = Theme.colors.onError,
                name = "Theme Color OnError",
                textColor = Theme.extendedTypographyColors.primary)
        }
    }
}

@Composable
private fun ColorSwatch(
    baseColor: Color,
    overlayColor: Color = baseColor,
    name: String,
    textColor: Color) {
    Spacer(modifier = Modifier.height(Theme.dimens.spacing.small))

    Row(modifier = Modifier
        .background(color = baseColor, shape = Theme.shapes.small)
        .height(50.dp)
        .fillMaxWidth()
        .border(width = Theme.dimens.one, color = Theme.colors.surface)
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = overlayColor
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Theme.dimens.spacing.small),
                text = name,
                style = Theme.extendedTypography.small,
                color = textColor)
        }
    }
}



