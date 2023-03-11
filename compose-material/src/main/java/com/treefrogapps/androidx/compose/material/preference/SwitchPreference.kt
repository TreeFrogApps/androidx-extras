package com.treefrogapps.androidx.compose.material.preference

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.treefrogapps.androidx.compose.material.R
import com.treefrogapps.androidx.compose.material.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material.theme.windowSizeOf
import kotlinx.coroutines.delay

@Composable
fun SwitchPreference(
    title: String,
    summary: String? = null,
    icon: Painter? = null,
    isVisible: Boolean = true,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
    preferenceColors: PreferenceColors = PreferenceDefaults.preferenceColors(),
) {
    CorePreference(
        title = title,
        summary = summary,
        icon = icon,
        isVisible = isVisible,
        enabled = enabled,
        colors = preferenceColors,
        onClick = { onCheckedChange?.invoke(!checked) },
        innerContent = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                interactionSource = interactionSource,
                colors = colors)
        })
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SwitchPreferencePreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var checkedA by remember { mutableStateOf(true) }
        var checkedB by remember { mutableStateOf(true) }
        var checkedC by remember { mutableStateOf(false) }
        PreferenceGroup(
            title = "Switch Group"
        ) {
            SwitchPreference(
                checked = checkedA,
                onCheckedChange = { checked -> checkedA = checked },
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
            SwitchPreference(
                checked = checkedB,
                onCheckedChange = { checked -> checkedB = checked },
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
            SwitchPreference(
                checked = checkedC,
                onCheckedChange = { checked -> checkedC = checked },
                isVisible = isVisible,
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
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
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SwitchPreferenceDarkPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var checkedA by remember { mutableStateOf(true) }
        var checkedB by remember { mutableStateOf(true) }
        var checkedC by remember { mutableStateOf(false) }
        PreferenceGroup(
            title = "Switch Group"
        ) {
            SwitchPreference(
                checked = checkedA,
                onCheckedChange = { checked -> checkedA = checked },
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
            SwitchPreference(
                checked = checkedB,
                onCheckedChange = { checked -> checkedB = checked },
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
            SwitchPreference(
                checked = checkedC,
                onCheckedChange = { checked -> checkedC = checked },
                isVisible = isVisible,
                title = "Switch preference field title",
                summary = "Switch preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge))
        }
        LaunchedEffect(key1 = Unit) {
            while (true) {
                delay(timeMillis = 3_000)
                isVisible = !isVisible
            }
        }
    }
}