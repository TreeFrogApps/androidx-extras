package com.treefrogapps.androidx.compose.material.preference

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.treefrogapps.androidx.compose.material.R
import com.treefrogapps.androidx.compose.material.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material.theme.Theme
import com.treefrogapps.androidx.compose.material.theme.windowSizeOf
import kotlinx.coroutines.delay

@Composable
fun RangePreference(
    title: String,
    icon: Painter? = null,
    isVisible: Boolean = true,
    min: Int,
    max: Int,
    current: Int?,
    onPreferenceChange: ((Int) -> Unit)?,
    enabled: Boolean = true,
    preferenceColors: PreferenceColors = PreferenceDefaults.preferenceColors(),
    sliderColors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    CorePreference(
        title = title,
        summary = current?.toString().orEmpty(),
        icon = icon,
        isVisible = isVisible,
        enabled = enabled,
        colors = preferenceColors,
        bottomContent = {
            Slider(
                modifier = Modifier.padding(horizontal = Theme.dimens.spacing.large),
                value = current?.toFloat() ?: min.toFloat(),
                onValueChange = { value -> onPreferenceChange?.invoke(value.toInt()) },
                enabled = enabled,
                valueRange = min.toFloat()..max.toFloat(),
                steps = (max - min).coerceAtLeast(minimumValue = 0),
                colors = sliderColors,
                interactionSource = interactionSource)
        })
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
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemA,
                    enabled = true,
                    onPreferenceChange = { selected -> selectedItemA = selected })
                RangePreference(
                    title = "Range preference field title",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemB,
                    enabled = false,
                    onPreferenceChange = { selected -> selectedItemB = selected })
                RangePreference(
                    title = "Range preference field title",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemC,
                    enabled = true,
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
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemA,
                    enabled = true,
                    onPreferenceChange = { selected -> selectedItemA = selected })
                RangePreference(
                    title = "Range preference field title",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemB,
                    enabled = false,
                    onPreferenceChange = { selected -> selectedItemB = selected })
                RangePreference(
                    title = "Range preference field title",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    min = 0,
                    max = 100,
                    current = selectedItemC,
                    enabled = true,
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