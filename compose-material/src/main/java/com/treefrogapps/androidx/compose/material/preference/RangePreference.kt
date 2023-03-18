package com.treefrogapps.androidx.compose.material.preference

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.treefrogapps.androidx.compose.material.R
import com.treefrogapps.androidx.compose.material.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material.theme.Theme
import com.treefrogapps.androidx.compose.material.theme.windowSizeOf
import kotlinx.coroutines.delay

@Composable
fun RangePreference(
    title: String,
    summary: String? = null,
    icon: Painter? = null,
    isVisible: Boolean = true,
    min: Int,
    max: Int,
    current: Int?,
    onPreferenceChange: ((Int) -> Unit)?,
    enabled: Boolean = true,
    preferenceColors: PreferenceColors = PreferenceDefaults.preferenceColors(),
    sliderTitle: String,
    sliderColors: SliderColors = SliderDefaults.colors(
        thumbColor = Theme.colors.secondary,
        activeTickColor = Theme.colors.secondary),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    var isDialogOpen by rememberSaveable { mutableStateOf(value = false) }

    CorePreference(
        title = title,
        summary = summary,
        information = current?.toString().orEmpty(),
        icon = icon,
        isVisible = isVisible,
        enabled = enabled,
        colors = preferenceColors,
        onClick = { isDialogOpen = true })

    if (isDialogOpen) {
        RangePreferenceDialog(
            sliderTitle = sliderTitle,
            sliderTitleColor = preferenceColors.titleColor(enabled = enabled).value,
            min = min,
            max = max,
            current = current ?: min,
            onPreferenceChange = onPreferenceChange,
            enabled = enabled,
            sliderColors = sliderColors,
            interactionSource = interactionSource,
            onDismiss = { isDialogOpen = false })
    }
}


@Composable
private fun RangePreferenceDialog(
    sliderTitle: String,
    sliderTitleColor: Color,
    min: Int,
    max: Int,
    current: Int,
    onPreferenceChange: ((Int) -> Unit)?,
    enabled: Boolean = true,
    sliderColors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        RangePreferenceDialogContent(
            sliderTitle = sliderTitle,
            sliderTitleColor = sliderTitleColor,
            min = min,
            max = max,
            current = current,
            onPreferenceChange = onPreferenceChange,
            enabled = enabled,
            sliderColors = sliderColors,
            interactionSource = interactionSource)
    }
}

@Composable
private fun RangePreferenceDialogContent(
    sliderTitle: String,
    sliderTitleColor: Color,
    min: Int,
    max: Int,
    current: Int,
    onPreferenceChange: ((Int) -> Unit)?,
    enabled: Boolean = true,
    sliderColors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Card {
        Column(
            modifier = Modifier
                .padding(all = Theme.dimens.spacing.large),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = sliderTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Theme.extendedTypography.large,
                color = sliderTitleColor)
            Slider(
                value = current.toFloat(),
                onValueChange = { value -> onPreferenceChange?.invoke(value.toInt()) },
                enabled = enabled,
                valueRange = min.toFloat()..max.toFloat(),
                steps = 0,
                colors = sliderColors,
                interactionSource = interactionSource)
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(end = Theme.dimens.spacing.small),
                text = current.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Theme.typography.body1,
                color = sliderColors.thumbColor(enabled = enabled).value)
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun RangePreferencePreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var selectedItemA by remember { mutableStateOf<Int?>(null) }
        var selectedItemB by remember { mutableStateOf<Int?>(50) }
        var selectedItemC by remember { mutableStateOf<Int?>(75) }
        PreferenceContainer {
            PreferenceGroup(
                title = "Range Group"
            ) {
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemA,
                    enabled = true,
                    sliderTitle = "Update Range Slider",
                    onPreferenceChange = { selected -> selectedItemA = selected })
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemB,
                    enabled = false,
                    sliderTitle = "Update Range Slider",
                    onPreferenceChange = { selected -> selectedItemB = selected })
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemC,
                    enabled = true,
                    sliderTitle = "Update Range Slider",
                    isVisible = isVisible,
                    onPreferenceChange = { selected -> selectedItemC = selected })
            }
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(timeMillis = 3_000)
                isVisible = !isVisible
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RangePreferenceDarkPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var selectedItemA by remember { mutableStateOf<Int?>(null) }
        var selectedItemB by remember { mutableStateOf<Int?>(50) }
        var selectedItemC by remember { mutableStateOf<Int?>(75) }

        PreferenceContainer {
            PreferenceGroup(
                title = "Range Group"
            ) {
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemA,
                    enabled = true,
                    sliderTitle = "Update Range Slider",
                    onPreferenceChange = { selected -> selectedItemA = selected })
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemB,
                    enabled = false,
                    sliderTitle = "Update Range Slider",
                    onPreferenceChange = { selected -> selectedItemB = selected })
                RangePreference(
                    title = "Range preference field title",
                    summary = "Range preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemC,
                    enabled = true,
                    sliderTitle = "Update Range Slider",
                    isVisible = isVisible,
                    onPreferenceChange = { selected -> selectedItemC = selected })
            }
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(timeMillis = 3_000)
                isVisible = !isVisible
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
private fun RangePreferenceDialogContentPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var selectedItemA by remember { mutableStateOf<Int?>(null) }

        PreferenceContainer {
            RangePreferenceDialogContent(
                min = 0,
                max = 100,
                current = selectedItemA ?: 0,
                enabled = true,
                sliderTitle = "Update Range Slider",
                sliderTitleColor = Theme.extendedTypographyColors.primary,
                onPreferenceChange = { selected -> selectedItemA = selected })
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(timeMillis = 3_000)
                isVisible = !isVisible
            }
        }
    }
}