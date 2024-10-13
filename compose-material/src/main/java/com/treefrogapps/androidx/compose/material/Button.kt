package com.treefrogapps.androidx.compose.material

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.treefrogapps.androidx.compose.material.theme.Theme

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textHorizontalPadding: Dp = Theme.dimens.spacing.large,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Theme.colors.secondary),
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