package com.treefrogapps.compose.material.theme

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
    typography: Typography = MaterialThemeExtended.typography,
    shapes: Shapes = MaterialThemeExtended.shapes,
    content: @Composable () -> Unit
) {
    MaterialThemeExtended(windowSize = windowSize) {
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
    appTypography: ExtendedTypography = MaterialThemeExtended.extendedTypography,
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
            .padding(MaterialThemeExtended.dimens.spacing.large)) {

            Text("Window ${MaterialThemeExtended.windowSize.name}", color = MaterialThemeExtended.colors.onBackground)
            Spacer(modifier = Modifier.height(MaterialThemeExtended.dimens.spacing.normal))
            Text(text = "Dimen zero ${MaterialThemeExtended.dimens.zero}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen hairline ${MaterialThemeExtended.dimens.hairline}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen one ${MaterialThemeExtended.dimens.one}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen elevation ${MaterialThemeExtended.dimens.elevation.normal}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen elevation high ${MaterialThemeExtended.dimens.elevation.high}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen elevation highest ${MaterialThemeExtended.dimens.elevation.highest}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing tiny ${MaterialThemeExtended.dimens.spacing.tiny}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing small ${MaterialThemeExtended.dimens.spacing.small}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing normal ${MaterialThemeExtended.dimens.spacing.normal}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing large ${MaterialThemeExtended.dimens.spacing.large}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing big ${MaterialThemeExtended.dimens.spacing.big}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing massive ${MaterialThemeExtended.dimens.spacing.massive}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing gigantic ${MaterialThemeExtended.dimens.spacing.gigantic}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen spacing enormous ${MaterialThemeExtended.dimens.spacing.enormous}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon minuscule ${MaterialThemeExtended.dimens.icon.minuscule}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon small ${MaterialThemeExtended.dimens.icon.small}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon notification ${MaterialThemeExtended.dimens.icon.notification}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon normal ${MaterialThemeExtended.dimens.icon.normal}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon large ${MaterialThemeExtended.dimens.icon.large}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon big ${MaterialThemeExtended.dimens.icon.big}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon massive ${MaterialThemeExtended.dimens.icon.massive}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon enormous ${MaterialThemeExtended.dimens.icon.enormous}", color = MaterialThemeExtended.colors.onBackground)
            Text(text = "Dimen icon thumbnail ${MaterialThemeExtended.dimens.icon.thumbnail}", color = MaterialThemeExtended.colors.onBackground)
        }

        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(MaterialThemeExtended.dimens.spacing.large)) {
            Text(
                text = "Small Text Primary",
                style = MaterialThemeExtended.extendedTypography.small,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Text(
                text = "Small Bold Text Primary",
                style = MaterialThemeExtended.extendedTypography.smallBold,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Text(
                text = "Medium Text Primary",
                style = MaterialThemeExtended.extendedTypography.medium,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Text(
                text = "Medium Bold Text Primary",
                style = MaterialThemeExtended.extendedTypography.mediumBold,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Text(
                text = "Large Text Primary",
                style = MaterialThemeExtended.extendedTypography.large,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Text(
                text = "Large Bold Text Primary",
                style = MaterialThemeExtended.extendedTypography.largeBold,
                color = MaterialThemeExtended.extendedTypographyColors.primary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = MaterialThemeExtended.colors.onBackground)) {

                Text(
                    text = "Small Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.small,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Small Bold Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.smallBold,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Medium Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.medium,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Medium Bold Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.mediumBold,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Large Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.large,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

                Text(
                    text = "Large Bold Text Primary Inverse",
                    style = MaterialThemeExtended.extendedTypography.largeBold,
                    color = MaterialThemeExtended.extendedTypographyColors.primaryInverse)
            }

            Text(
                text = "Small Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.small,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Small Bold Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.smallBold,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Medium Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.medium,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Medium Bold Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.mediumBold,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Large Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.large,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Large Bold Text Primary Variant",
                style = MaterialThemeExtended.extendedTypography.largeBold,
                color = MaterialThemeExtended.extendedTypographyColors.primaryVariant)

            Text(
                text = "Small Text Secondary",
                style = MaterialThemeExtended.extendedTypography.small,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Text(
                text = "Small Bold Text Secondary",
                style = MaterialThemeExtended.extendedTypography.smallBold,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Text(
                text = "Medium Text Secondary",
                style = MaterialThemeExtended.extendedTypography.medium,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Text(
                text = "Medium Bold Text Secondary",
                style = MaterialThemeExtended.extendedTypography.mediumBold,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Text(
                text = "Large Text Secondary",
                style = MaterialThemeExtended.extendedTypography.large,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Text(
                text = "Large Bold Text Secondary",
                style = MaterialThemeExtended.extendedTypography.largeBold,
                color = MaterialThemeExtended.extendedTypographyColors.secondary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = MaterialThemeExtended.colors.onBackground)) {

                Text(
                    text = "Small Text Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.small,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Small Bold Text Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.smallBold,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Text Primary Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.medium,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Bold Text Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.mediumBold,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Large Text Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.large,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)

                Text(
                    text = "Large Bold Text Secondary Inverse",
                    style = MaterialThemeExtended.extendedTypography.largeBold,
                    color = MaterialThemeExtended.extendedTypographyColors.secondaryInverse)
            }
        }


        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(MaterialThemeExtended.dimens.spacing.large)) {

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.primary,
                name = "Theme Color Primary",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.primaryVariant,
                name = "Theme Color PrimaryVariant",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.secondary,
                name = "Theme Color Secondary",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.secondaryVariant,
                name = "Theme Color SecondaryVariant",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.background,
                name = "Theme Color Background",
                textColor = MaterialThemeExtended.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.surface,
                name = "Theme Color surface",
                textColor = MaterialThemeExtended.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.error,
                name = "Theme Color Error",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.primary,
                overlayColor = MaterialThemeExtended.colors.onPrimary,
                name = "Theme Color OnPrimary",
                textColor = MaterialThemeExtended.extendedTypographyColors.primary)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.secondary,
                overlayColor = MaterialThemeExtended.colors.onSecondary,
                name = "Theme Color OnSecondary",
                textColor = MaterialThemeExtended.extendedTypographyColors.secondary)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.background,
                overlayColor = MaterialThemeExtended.colors.onBackground,
                name = "Theme Color OnBackground",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.surface,
                overlayColor = MaterialThemeExtended.colors.onSurface,
                name = "Theme Color OnSurface",
                textColor = MaterialThemeExtended.extendedTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = MaterialThemeExtended.colors.error,
                overlayColor = MaterialThemeExtended.colors.onError,
                name = "Theme Color OnError",
                textColor = MaterialThemeExtended.extendedTypographyColors.primary)
        }
    }
}

@Composable
private fun ColorSwatch(
    baseColor: Color,
    overlayColor: Color = baseColor,
    name: String,
    textColor: Color) {
    Spacer(modifier = Modifier.height(MaterialThemeExtended.dimens.spacing.small))

    Row(modifier = Modifier
        .background(color = baseColor, shape = MaterialThemeExtended.shapes.small)
        .height(50.dp)
        .fillMaxWidth()
        .border(width = MaterialThemeExtended.dimens.one, color = MaterialThemeExtended.colors.surface)
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = overlayColor
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialThemeExtended.dimens.spacing.small),
                text = name,
                style = MaterialThemeExtended.extendedTypography.small,
                color = textColor)
        }
    }
}



