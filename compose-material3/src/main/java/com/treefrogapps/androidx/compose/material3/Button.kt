package com.treefrogapps.androidx.compose.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.treefrogapps.androidx.compose.material3.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material3.theme.Theme
import com.treefrogapps.androidx.compose.material3.theme.windowSizeOf

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textHorizontalPadding: Dp = Theme.dimens.spacing.large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Theme.colorScheme.secondary),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        enabled = enabled,
        onClick = onClick,
        colors = colors,
        interactionSource = interactionSource,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        Text(
            modifier = Modifier.padding(horizontal = textHorizontalPadding),
            fontSize = 16.sp,
            text = text
        )
    }
}

@Preview
@Composable
private fun RoundedButtonPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        Surface {
            RoundedButton(
                text = "Rounded Button",
                onClick = { }
            )
        }
    }
}