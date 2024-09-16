package com.treefrogapps.androidx.compose.material3.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Type

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

    val colorScheme: ColorScheme
        @ReadOnlyComposable
        @Composable
        get() = MaterialTheme.colorScheme

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
    colorScheme: ColorScheme = provideDefaultColors(isDarkMode),
    typography: Typography = Theme.typography,
    extendedTypography: ExtendedTypography = Theme.extendedTypography,
    extendedTypographyColors: ExtendedTypographyColors = provideDefaultTypographyColors(isDarkMode),
    shapes: Shapes = DefaultShapes,
    content: @Composable () -> Unit
) {
    MaterialThemeExtendedInternal(
        windowSize = windowSize,
        isDarkMode = isDarkMode,
        extendedTypography = extendedTypography,
        extendedTypographyColors = extendedTypographyColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

@Composable
private fun MaterialThemeExtendedInternal(
    windowSize: WindowSize,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    extendedTypography: ExtendedTypography = Theme.extendedTypography,
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
            LocalExtendedTypography provides extendedTypography,
            LocalExtendedTypographyColors provides rememberedTypographyColors
        ),
        content = content
    )
}

@Preview(
    name = "Tablet",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_C
)
@Composable
fun ThemedContentPreview() {
    MaterialThemeExtendedInternal(windowSize = windowSizeOf(Type.LargeLandscape)) {
        PreviewContent()
    }
}

@Preview(
    name = "Tablet Dark Mode",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Composable
fun ThemedContentDarkModePreview() {
    MaterialThemeExtendedInternal(windowSize = windowSizeOf(Type.LargeLandscape)) {
        PreviewContent()
    }
}

@Composable
fun PreviewContent() {
    Row(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .weight(1.0F)
                .verticalScroll(rememberScrollState())
                .padding(Theme.dimens.spacing.large)
        ) {

            Text("Window ${Theme.windowSize.name}", color = Theme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(Theme.dimens.spacing.normal))
            Text(text = "Dimen zero ${Theme.dimens.zero}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen hairline ${Theme.dimens.hairline}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen one ${Theme.dimens.one}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen elevation ${Theme.dimens.elevation.normal}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen elevation high ${Theme.dimens.elevation.high}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen elevation highest ${Theme.dimens.elevation.highest}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing tiny ${Theme.dimens.spacing.tiny}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing small ${Theme.dimens.spacing.small}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing normal ${Theme.dimens.spacing.normal}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing large ${Theme.dimens.spacing.large}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing big ${Theme.dimens.spacing.big}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing massive ${Theme.dimens.spacing.massive}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing gigantic ${Theme.dimens.spacing.gigantic}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen spacing enormous ${Theme.dimens.spacing.enormous}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon minuscule ${Theme.dimens.icon.minuscule}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon small ${Theme.dimens.icon.small}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon notification ${Theme.dimens.icon.notification}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon normal ${Theme.dimens.icon.normal}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon large ${Theme.dimens.icon.large}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon big ${Theme.dimens.icon.big}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon massive ${Theme.dimens.icon.massive}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon enormous ${Theme.dimens.icon.enormous}", color = Theme.colorScheme.onBackground)
            Text(text = "Dimen icon thumbnail ${Theme.dimens.icon.thumbnail}", color = Theme.colorScheme.onBackground)
        }

        Column(
            modifier = Modifier
                .weight(1.0F)
                .verticalScroll(rememberScrollState())
                .padding(Theme.dimens.spacing.large)
        ) {
            Text(
                text = "Small Text Primary",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.primary
            )

            Text(
                text = "Small Bold Text Primary",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.primary
            )

            Text(
                text = "Medium Text Primary",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.primary
            )

            Text(
                text = "Medium Bold Text Primary",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.primary
            )

            Text(
                text = "Large Text Primary",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.primary
            )

            Text(
                text = "Large Bold Text Primary",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.primary
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(color = Theme.colorScheme.onBackground)
            ) {

                Text(
                    text = "Small Text Primary Inverse",
                    style = Theme.extendedTypography.small,
                    color = Theme.extendedTypographyColors.primaryInverse
                )

                Text(
                    text = "Small Bold Text Primary Inverse",
                    style = Theme.extendedTypography.smallBold,
                    color = Theme.extendedTypographyColors.primaryInverse
                )

                Text(
                    text = "Medium Text Primary Inverse",
                    style = Theme.extendedTypography.medium,
                    color = Theme.extendedTypographyColors.primaryInverse
                )

                Text(
                    text = "Medium Bold Text Primary Inverse",
                    style = Theme.extendedTypography.mediumBold,
                    color = Theme.extendedTypographyColors.primaryInverse
                )

                Text(
                    text = "Large Text Primary Inverse",
                    style = Theme.extendedTypography.large,
                    color = Theme.extendedTypographyColors.primaryInverse
                )

                Text(
                    text = "Large Bold Text Primary Inverse",
                    style = Theme.extendedTypography.largeBold,
                    color = Theme.extendedTypographyColors.primaryInverse
                )
            }

            Text(
                text = "Small Text Primary Variant",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Small Bold Text Primary Variant",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Medium Text Primary Variant",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Medium Bold Text Primary Variant",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Large Text Primary Variant",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Large Bold Text Primary Variant",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.primaryVariant
            )

            Text(
                text = "Small Text Secondary",
                style = Theme.extendedTypography.small,
                color = Theme.extendedTypographyColors.secondary
            )

            Text(
                text = "Small Bold Text Secondary",
                style = Theme.extendedTypography.smallBold,
                color = Theme.extendedTypographyColors.secondary
            )

            Text(
                text = "Medium Text Secondary",
                style = Theme.extendedTypography.medium,
                color = Theme.extendedTypographyColors.secondary
            )

            Text(
                text = "Medium Bold Text Secondary",
                style = Theme.extendedTypography.mediumBold,
                color = Theme.extendedTypographyColors.secondary
            )

            Text(
                text = "Large Text Secondary",
                style = Theme.extendedTypography.large,
                color = Theme.extendedTypographyColors.secondary
            )

            Text(
                text = "Large Bold Text Secondary",
                style = Theme.extendedTypography.largeBold,
                color = Theme.extendedTypographyColors.secondary
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(color = Theme.colorScheme.onBackground)
            ) {

                Text(
                    text = "Small Text Secondary Inverse",
                    style = Theme.extendedTypography.small,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )

                Text(
                    text = "Small Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.smallBold,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )

                Text(
                    text = "Medium Text Primary Secondary Inverse",
                    style = Theme.extendedTypography.medium,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )

                Text(
                    text = "Medium Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.mediumBold,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )

                Text(
                    text = "Large Text Secondary Inverse",
                    style = Theme.extendedTypography.large,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )

                Text(
                    text = "Large Bold Text Secondary Inverse",
                    style = Theme.extendedTypography.largeBold,
                    color = Theme.extendedTypographyColors.secondaryInverse
                )
            }
        }


        Column(
            modifier = Modifier
                .weight(1.0F)
                .verticalScroll(rememberScrollState())
                .padding(Theme.dimens.spacing.large)
        ) {

            ColorSwatch(
                baseColor = Theme.colorScheme.primary,
                name = "Theme Color Primary",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.onPrimary,
                name = "Theme Color OnPrimary",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.primaryContainer,
                name = "Theme Color PrimaryContainer",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.onPrimaryContainer,
                name = "Theme Color OnPrimaryContainer",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.secondary,
                name = "Theme Color Secondary",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.onSecondary,
                name = "Theme Color On Secondary",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.secondaryContainer,
                name = "Theme Color SecondaryContainer",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.onSecondaryContainer,
                name = "Theme Color OnSecondaryContainer",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.background,
                name = "Theme Color Background",
                textColor = Theme.extendedTypographyColors.primary
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.surface,
                name = "Theme Color surface",
                textColor = Theme.extendedTypographyColors.primary
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.error,
                name = "Theme Color Error",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.primary,
                overlayColor = Theme.colorScheme.onPrimary,
                name = "Theme Color OnPrimary",
                textColor = Theme.extendedTypographyColors.primary
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.secondary,
                overlayColor = Theme.colorScheme.onSecondary,
                name = "Theme Color OnSecondary",
                textColor = Theme.extendedTypographyColors.secondary
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.background,
                overlayColor = Theme.colorScheme.onBackground,
                name = "Theme Color OnBackground",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.surface,
                overlayColor = Theme.colorScheme.onSurface,
                name = "Theme Color OnSurface",
                textColor = Theme.extendedTypographyColors.primaryInverse
            )

            ColorSwatch(
                baseColor = Theme.colorScheme.error,
                overlayColor = Theme.colorScheme.onError,
                name = "Theme Color OnError",
                textColor = Theme.extendedTypographyColors.primary
            )
        }
    }
}

@Composable
private fun ColorSwatch(
    baseColor: Color,
    overlayColor: Color = baseColor,
    name: String,
    textColor: Color
) {
    Spacer(modifier = Modifier.height(Theme.dimens.spacing.small))

    Row(
        modifier = Modifier
            .background(color = baseColor, shape = Theme.shapes.small)
            .height(50.dp)
            .fillMaxWidth()
            .border(width = Theme.dimens.one, color = Theme.colorScheme.surface)
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
                color = textColor
            )
        }
    }
}



