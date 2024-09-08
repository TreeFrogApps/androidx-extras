package com.treefrogapps.androidx.compose.material.preference

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.treefrogapps.androidx.compose.material.R
import com.treefrogapps.androidx.compose.material.TextRadioButton
import com.treefrogapps.androidx.compose.material.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material.theme.Theme
import com.treefrogapps.androidx.compose.material.theme.windowSizeOf
import kotlinx.coroutines.delay

@Composable
fun <T : Any> ListPreference(
    title: String,
    summary: String? = null,
    icon: Painter? = null,
    isVisible: Boolean = true,
    items: List<T>,
    itemNameProvider: (T) -> String = { it.toString() },
    selectedItem: T?,
    onPreferenceChange: ((T) -> Unit)?,
    enabled: Boolean = true,
    preferenceColors: PreferenceColors = PreferenceDefaults.preferenceColors(),
) {
    var isDialogOpen by rememberSaveable { mutableStateOf(value = false) }

    CorePreference(
        title = title,
        summary = summary,
        information = selectedItem?.let(itemNameProvider::invoke),
        icon = icon,
        isVisible = isVisible,
        enabled = enabled,
        colors = preferenceColors,
        onClick = { isDialogOpen = true },
        innerContent = { })

    if (isDialogOpen) {
        ListPreferenceDialog(
            items = items,
            itemNameProvider = itemNameProvider,
            selectedItem = selectedItem,
            onPreferenceChange = onPreferenceChange,
            onDismiss = { isDialogOpen = false })
    }
}

@Composable
private fun <T : Any> ListPreferenceDialog(
    items: List<T>,
    itemNameProvider: (T) -> String,
    selectedItem: T?,
    onPreferenceChange: ((T) -> Unit)?,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(all = Theme.dimens.spacing.normal)
                    .defaultMinSize(minWidth = 240.dp)
            ) {
                items.forEach { item ->
                    val name = remember { itemNameProvider(item) }

                    TextRadioButton(
                        text = name,
                        selected = item == selectedItem,
                        onClick = {
                            onPreferenceChange?.invoke(item)
                            onDismiss()
                        })
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ListPreferencePreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var selectedItemA by remember { mutableStateOf<String?>(null) }
        var selectedItemB by remember { mutableStateOf<String?>("Item one") }
        var selectedItemC by remember { mutableStateOf<String?>("Item three") }

        PreferenceContainer {
            PreferenceGroup(
                title = "List Group"
            ) {
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemA,
                    enabled = true,
                    onPreferenceChange = { selected -> selectedItemA = selected })
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemB,
                    enabled = false,
                    onPreferenceChange = { selected -> selectedItemB = selected })
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemC,
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
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ListPreferenceDarkPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        var selectedItemA by remember { mutableStateOf<String?>(null) }
        var selectedItemB by remember { mutableStateOf<String?>("Item one") }
        var selectedItemC by remember { mutableStateOf<String?>("Item three") }

        PreferenceContainer {
            PreferenceGroup(
                title = "List Group"
            ) {
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemA,
                    enabled = true,
                    onPreferenceChange = { selected -> selectedItemA = selected })
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemB,
                    enabled = false,
                    onPreferenceChange = { selected -> selectedItemB = selected })
                ListPreference(
                    title = "List preference field title",
                    summary = "List preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge),
                    itemNameProvider = String::toString,
                    items = listOf(
                        "Item one",
                        "Item two",
                        "Item three"
                    ),
                    selectedItem = selectedItemC,
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