package com.treefrogapps.compose.parallax.example.ui.theme

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


object AppTheme {

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

    val appTypography: AppTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalAppTypography.current

    val appTypographyColors: AppTypographyColors
        @ReadOnlyComposable
        @Composable
        get() = LocalAppTypographyColors.current

    val shapes: Shapes
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.shapes
}

@Composable
fun AppThemedContent(
    windowSize: WindowSize,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    colors: Colors = provideDefaultColors(isDarkMode),
    typography: Typography = AppTheme.typography,
    shapes: Shapes = AppTheme.shapes,
    content: @Composable () -> Unit
) {
    AppThemeWrapper(windowSize = windowSize) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content)
    }
}

@Composable
private fun AppThemeWrapper(
    windowSize: WindowSize,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    appTypography: AppTypography = AppTheme.appTypography,
    appTypographyColors: AppTypographyColors = provideDefaultTypographyColors(isDarkMode),
    content: @Composable () -> Unit
) {

    val rememberedWindowSize = remember { windowSize }
    val rememberedDimens = remember(windowSize) { windowSize.toDimens() }
    val rememberedTypographyColors = remember { appTypographyColors.copy() }.apply { updateColorsFrom(appTypographyColors) }

    CompositionLocalProvider(
        values = arrayOf(
            LocalWindowSize provides rememberedWindowSize,
            LocalAppDimens provides rememberedDimens,
            LocalAppTypography provides appTypography,
            LocalAppTypographyColors provides rememberedTypographyColors),
        content = content)
}

@Preview(
    name = "Tablet",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_C)
@Composable
fun ThemedContentPreview() {
    AppThemedContent(windowSize = WindowSize.LargeLandscape) {
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
    AppThemedContent(windowSize = WindowSize.LargeLandscape) {
        PreviewContent()
    }
}

@Composable
fun PreviewContent() {
    Row(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(AppTheme.dimens.spacing.large)) {

            Text("Window ${AppTheme.windowSize.name}", color = AppTheme.colors.onBackground)
            Spacer(modifier = Modifier.height(AppTheme.dimens.spacing.normal))
            Text(text = "Dimen zero ${AppTheme.dimens.zero}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen hairline ${AppTheme.dimens.hairline}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen one ${AppTheme.dimens.one}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen elevation ${AppTheme.dimens.elevation.normal}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen elevation high ${AppTheme.dimens.elevation.high}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen elevation highest ${AppTheme.dimens.elevation.highest}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing tiny ${AppTheme.dimens.spacing.tiny}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing small ${AppTheme.dimens.spacing.small}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing normal ${AppTheme.dimens.spacing.normal}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing large ${AppTheme.dimens.spacing.large}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing big ${AppTheme.dimens.spacing.big}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing massive ${AppTheme.dimens.spacing.massive}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing gigantic ${AppTheme.dimens.spacing.gigantic}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen spacing enormous ${AppTheme.dimens.spacing.enormous}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon minuscule ${AppTheme.dimens.icon.minuscule}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon small ${AppTheme.dimens.icon.small}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon notification ${AppTheme.dimens.icon.notification}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon normal ${AppTheme.dimens.icon.normal}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon large ${AppTheme.dimens.icon.large}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon big ${AppTheme.dimens.icon.big}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon massive ${AppTheme.dimens.icon.massive}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon enormous ${AppTheme.dimens.icon.enormous}", color = AppTheme.colors.onBackground)
            Text(text = "Dimen icon thumbnail ${AppTheme.dimens.icon.thumbnail}", color = AppTheme.colors.onBackground)
        }

        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(AppTheme.dimens.spacing.large)) {
            Text(
                text = "Small Text Primary",
                style = AppTheme.appTypography.small,
                color = AppTheme.appTypographyColors.primary)

            Text(
                text = "Small Bold Text Primary",
                style = AppTheme.appTypography.smallBold,
                color = AppTheme.appTypographyColors.primary)

            Text(
                text = "Medium Text Primary",
                style = AppTheme.appTypography.medium,
                color = AppTheme.appTypographyColors.primary)

            Text(
                text = "Medium Bold Text Primary",
                style = AppTheme.appTypography.mediumBold,
                color = AppTheme.appTypographyColors.primary)

            Text(
                text = "Large Text Primary",
                style = AppTheme.appTypography.large,
                color = AppTheme.appTypographyColors.primary)

            Text(
                text = "Large Bold Text Primary",
                style = AppTheme.appTypography.largeBold,
                color = AppTheme.appTypographyColors.primary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = AppTheme.colors.onBackground)) {

                Text(
                    text = "Small Text Primary Inverse",
                    style = AppTheme.appTypography.small,
                    color = AppTheme.appTypographyColors.primaryInverse)

                Text(
                    text = "Small Bold Text Primary Inverse",
                    style = AppTheme.appTypography.smallBold,
                    color = AppTheme.appTypographyColors.primaryInverse)

                Text(
                    text = "Medium Text Primary Inverse",
                    style = AppTheme.appTypography.medium,
                    color = AppTheme.appTypographyColors.primaryInverse)

                Text(
                    text = "Medium Bold Text Primary Inverse",
                    style = AppTheme.appTypography.mediumBold,
                    color = AppTheme.appTypographyColors.primaryInverse)

                Text(
                    text = "Large Text Primary Inverse",
                    style = AppTheme.appTypography.large,
                    color = AppTheme.appTypographyColors.primaryInverse)

                Text(
                    text = "Large Bold Text Primary Inverse",
                    style = AppTheme.appTypography.largeBold,
                    color = AppTheme.appTypographyColors.primaryInverse)
            }

            Text(
                text = "Small Text Primary Variant",
                style = AppTheme.appTypography.small,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Small Bold Text Primary Variant",
                style = AppTheme.appTypography.smallBold,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Medium Text Primary Variant",
                style = AppTheme.appTypography.medium,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Medium Bold Text Primary Variant",
                style = AppTheme.appTypography.mediumBold,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Large Text Primary Variant",
                style = AppTheme.appTypography.large,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Large Bold Text Primary Variant",
                style = AppTheme.appTypography.largeBold,
                color = AppTheme.appTypographyColors.primaryVariant)

            Text(
                text = "Small Text Secondary",
                style = AppTheme.appTypography.small,
                color = AppTheme.appTypographyColors.secondary)

            Text(
                text = "Small Bold Text Secondary",
                style = AppTheme.appTypography.smallBold,
                color = AppTheme.appTypographyColors.secondary)

            Text(
                text = "Medium Text Secondary",
                style = AppTheme.appTypography.medium,
                color = AppTheme.appTypographyColors.secondary)

            Text(
                text = "Medium Bold Text Secondary",
                style = AppTheme.appTypography.mediumBold,
                color = AppTheme.appTypographyColors.secondary)

            Text(
                text = "Large Text Secondary",
                style = AppTheme.appTypography.large,
                color = AppTheme.appTypographyColors.secondary)

            Text(
                text = "Large Bold Text Secondary",
                style = AppTheme.appTypography.largeBold,
                color = AppTheme.appTypographyColors.secondary)

            Column(modifier = Modifier
                .wrapContentHeight()
                .background(color = AppTheme.colors.onBackground)) {

                Text(
                    text = "Small Text Secondary Inverse",
                    style = AppTheme.appTypography.small,
                    color = AppTheme.appTypographyColors.secondaryInverse)

                Text(
                    text = "Small Bold Text Secondary Inverse",
                    style = AppTheme.appTypography.smallBold,
                    color = AppTheme.appTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Text Primary Secondary Inverse",
                    style = AppTheme.appTypography.medium,
                    color = AppTheme.appTypographyColors.secondaryInverse)

                Text(
                    text = "Medium Bold Text Secondary Inverse",
                    style = AppTheme.appTypography.mediumBold,
                    color = AppTheme.appTypographyColors.secondaryInverse)

                Text(
                    text = "Large Text Secondary Inverse",
                    style = AppTheme.appTypography.large,
                    color = AppTheme.appTypographyColors.secondaryInverse)

                Text(
                    text = "Large Bold Text Secondary Inverse",
                    style = AppTheme.appTypography.largeBold,
                    color = AppTheme.appTypographyColors.secondaryInverse)
            }

            Text(
                text = "Small Text Accent",
                style = AppTheme.appTypography.small,
                color = AppTheme.appTypographyColors.accent)

            Text(
                text = "Small Bold Text Accent",
                style = AppTheme.appTypography.smallBold,
                color = AppTheme.appTypographyColors.accent)

            Text(
                text = "Medium Text Accent",
                style = AppTheme.appTypography.medium,
                color = AppTheme.appTypographyColors.accent)

            Text(
                text = "Medium Bold Text Accent",
                style = AppTheme.appTypography.mediumBold,
                color = AppTheme.appTypographyColors.accent)

            Text(
                text = "Large Text Accent",
                style = AppTheme.appTypography.large,
                color = AppTheme.appTypographyColors.accent)

            Text(
                text = "Large Bold Text Accent",
                style = AppTheme.appTypography.largeBold,
                color = AppTheme.appTypographyColors.accent)
        }


        Column(modifier = Modifier
            .weight(1.0F)
            .verticalScroll(rememberScrollState())
            .padding(AppTheme.dimens.spacing.large)) {

            ColorSwatch(
                baseColor = AppTheme.colors.primary,
                name = "Theme Color Primary",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.primaryVariant,
                name = "Theme Color PrimaryVariant",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.secondary,
                name = "Theme Color Secondary",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.secondaryVariant,
                name = "Theme Color SecondaryVariant",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.background,
                name = "Theme Color Background",
                textColor = AppTheme.appTypographyColors.primary)

            ColorSwatch(
                baseColor = AppTheme.colors.surface,
                name = "Theme Color surface",
                textColor = AppTheme.appTypographyColors.primary)

            ColorSwatch(
                baseColor = AppTheme.colors.error,
                name = "Theme Color Error",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.primary,
                overlayColor = AppTheme.colors.onPrimary,
                name = "Theme Color OnPrimary",
                textColor = AppTheme.appTypographyColors.primary)

            ColorSwatch(
                baseColor = AppTheme.colors.secondary,
                overlayColor = AppTheme.colors.onSecondary,
                name = "Theme Color OnSecondary",
                textColor = AppTheme.appTypographyColors.secondary)

            ColorSwatch(
                baseColor = AppTheme.colors.background,
                overlayColor = AppTheme.colors.onBackground,
                name = "Theme Color OnBackground",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.surface,
                overlayColor = AppTheme.colors.onSurface,
                name = "Theme Color OnSurface",
                textColor = AppTheme.appTypographyColors.primaryInverse)

            ColorSwatch(
                baseColor = AppTheme.colors.error,
                overlayColor = AppTheme.colors.onError,
                name = "Theme Color OnError",
                textColor = AppTheme.appTypographyColors.primary)
        }
    }
}

@Composable
private fun ColorSwatch(
    baseColor: Color,
    overlayColor: Color = baseColor,
    name: String,
    textColor: Color) {
    Spacer(modifier = Modifier.height(AppTheme.dimens.spacing.small))

    Row(modifier = Modifier
        .background(color = baseColor, shape = AppTheme.shapes.small)
        .height(50.dp)
        .fillMaxWidth()
        .border(width = AppTheme.dimens.one, color = AppTheme.colors.surface)
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = overlayColor
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.spacing.small),
                text = name,
                style = AppTheme.appTypography.small,
                color = textColor)
        }
    }
}



