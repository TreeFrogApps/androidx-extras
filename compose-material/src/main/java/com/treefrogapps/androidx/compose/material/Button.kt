package com.treefrogapps.androidx.compose.material

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.treefrogapps.androidx.compose.material.theme.Theme

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    textHorizontalPadding: Dp = Theme.dimens.spacing.large,
    backgroundColor: Color = Theme.colors.secondary,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(percent = 50),
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor = Theme.colors.primary)),
        interactionSource = interactionSource,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding
    ) {
        Text(
            modifier = Modifier.padding(horizontal = textHorizontalPadding),
            fontSize = 16.sp,
            text = text)
    }
}