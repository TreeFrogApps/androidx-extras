package com.treefrogapps.androidx.compose.material3.preference

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.treefrogapps.androidx.compose.material3.R
import com.treefrogapps.androidx.compose.material3.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material3.theme.Theme
import com.treefrogapps.androidx.compose.material3.theme.windowSizeOf
import kotlinx.coroutines.delay


@Composable
fun CorePreference(
    title: String,
    summary: String? = null,
    information: String? = null,
    icon: Painter? = null,
    isVisible: Boolean = true,
    enabled: Boolean = true,
    colors: PreferenceColors = PreferenceDefaults.preferenceColors(),
    onClick: () -> Unit = {},
    innerContent: @Composable RowScope.() -> Unit = {},
    bottomContent: @Composable ColumnScope.() -> Unit = {},
) {
    val titleColor by colors.titleColor(enabled = enabled)
    val summaryColor by colors.summaryColor(enabled = enabled)
    val informationColor by colors.informationColor(enabled = enabled)
    val iconColor by colors.iconColor(enabled = enabled)

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        enter = fadeIn(),
        exit = fadeOut(),
        visible = isVisible
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = enabled,
                    onClick = onClick
                )
        ) {
            Row(
                modifier = Modifier.padding(all = Theme.dimens.spacing.normal),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon?.let { ic ->
                    Icon(
                        modifier = Modifier
                            .size(size = Theme.dimens.icon.big)
                            .padding(end = Theme.dimens.spacing.normal),
                        painter = ic,
                        tint = iconColor,
                        contentDescription = title
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(weight = 1.0F)
                        .padding(end = Theme.dimens.spacing.normal),
                    verticalArrangement = Arrangement.spacedBy(space = Theme.dimens.spacing.tiny)
                ) {
                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        style = Theme.typography.titleLarge,
                        color = titleColor
                    )
                    summary?.run {
                        Text(
                            text = this,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            style = Theme.typography.bodyMedium,
                            color = summaryColor
                        )
                    }
                    information?.run {
                        Text(
                            text = this,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = Theme.typography.bodyLarge,
                            color = informationColor
                        )
                    }
                }
                innerContent()
            }
            bottomContent()
        }
    }
}

@Composable
fun PreferenceGroup(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = Theme.extendedTypographyColors.primary,
    enabledDivider: Boolean = false,
    groupContent: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Theme.dimens.spacing.normal)
    ) {
        Text(
            modifier = Modifier.padding(
                start = Theme.dimens.spacing.normal,
                end = Theme.dimens.spacing.normal,
                bottom = Theme.dimens.spacing.small
            ),
            text = title,
            color = titleColor,
            style = Theme.typography.titleSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        groupContent()

        if (enabledDivider) {
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun PreferenceContainer(
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    state: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = color,
        contentColor = contentColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = state)
        ) {
            content()
        }
    }
}


object PreferenceDefaults {

    @Composable
    fun preferenceColors(
        titleColor: Color = Theme.extendedTypographyColors.primary,
        summaryColor: Color = Theme.extendedTypographyColors.secondaryVariant,
        informationColor: Color = Theme.colorScheme.secondary,
        iconColor: Color = Theme.extendedTypographyColors.primary,
        disabledColor: Color = Theme.colorScheme.onSurface.copy(alpha = 0.12f).compositeOver(Theme.colorScheme.surface)
    ): PreferenceColors = DefaultPreferenceColors(
        titleColor = titleColor,
        summaryColor = summaryColor,
        informationColor = informationColor,
        iconColor = iconColor,
        disabledColor = disabledColor
    )
}

@Stable
interface PreferenceColors {

    @Composable
    fun titleColor(enabled: Boolean): State<Color>

    @Composable
    fun summaryColor(enabled: Boolean): State<Color>

    @Composable
    fun iconColor(enabled: Boolean): State<Color>

    @Composable
    fun informationColor(enabled: Boolean): State<Color>
}

/**
 * Default [PreferenceColors] implementation.
 */
@Immutable
private class DefaultPreferenceColors(
    private val titleColor: Color,
    private val summaryColor: Color,
    private val informationColor: Color,
    private val iconColor: Color,
    private val disabledColor: Color
) : PreferenceColors {

    @Composable
    override fun titleColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) titleColor else disabledColor)

    @Composable
    override fun summaryColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) summaryColor else disabledColor)

    @Composable
    override fun informationColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) informationColor else disabledColor)

    @Composable
    override fun iconColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) iconColor else disabledColor)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DefaultPreferenceColors) return false

        if (titleColor != other.titleColor) return false
        if (summaryColor != other.summaryColor) return false
        if (informationColor != other.informationColor) return false
        if (iconColor != other.iconColor) return false
        if (disabledColor != other.disabledColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = titleColor.hashCode()
        result = 31 * result + summaryColor.hashCode()
        result = 31 * result + informationColor.hashCode()
        result = 31 * result + iconColor.hashCode()
        result = 31 * result + disabledColor.hashCode()
        return result
    }
}



@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun CorePreferencePreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        Column {
            CorePreference(
                title = "Core preference field title",
                summary = "Core preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
            }
            CorePreference(
                title = "Core preference field title",
                summary = "Core preference field summary",
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
            }
            CorePreference(
                title = "Core preference field title with long text",
                summary = "Core preference field summary",
                isVisible = isVisible,
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun CorePreferenceDarkPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        Column {
            CorePreference(
                title = "Core preference field title",
                summary = "Core preference field summary",
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
            }
            CorePreference(
                title = "Core preference field title",
                summary = "Core preference field summary",
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
            }
            CorePreference(
                title = "Core preference field title",
                summary = "Core preference field summary",
                isVisible = isVisible,
                enabled = false,
                icon = painterResource(id = R.drawable.ic_confirmation_badge)
            ) {
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
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun PreferenceGroupPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        var isVisible by remember { mutableStateOf(true) }
        Column {
            PreferenceGroup(
                title = "Preference Group A"
            ) {
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    enabled = false,
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    isVisible = isVisible,
                    enabled = false,
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
            }
            PreferenceGroup(
                title = "Preference Group B"
            ) {
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    enabled = false,
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
                CorePreference(
                    title = "Core preference field title",
                    summary = "Core preference field summary",
                    isVisible = isVisible,
                    enabled = false,
                    icon = painterResource(id = R.drawable.ic_confirmation_badge)
                ) {
                }
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