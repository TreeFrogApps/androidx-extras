package com.treefrogapps.androidx.compose.material

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import com.treefrogapps.androidx.compose.material.theme.MaterialThemeExtended
import com.treefrogapps.androidx.compose.material.theme.windowSizeOf


@Composable
fun AutoResizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current,
    overflowWidthScale: Float = 0.90f
) {
    var shouldShow by remember { mutableStateOf(value = false) }
    var fontSizeScale by remember { mutableFloatStateOf(value = 1.0f) }
    val internalFontSize = if (fontSize.isSpecified) fontSize.times(other = fontSizeScale) else fontSize
    val internalStyle = if (style.fontSize.isSpecified) style.copy(fontSize = style.fontSize.times(other = fontSizeScale)) else style

    Text(
        text = text,
        modifier = modifier.drawWithContent { if (shouldShow) drawContent() },
        color = color,
        fontSize = internalFontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = false,
        maxLines = 1,
        minLines = 1,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                fontSizeScale *= overflowWidthScale
            } else {
                shouldShow = true
            }
        },
        style = internalStyle
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun AutoResizeTextPreview() {
    MaterialThemeExtended(windowSize = windowSizeOf()) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier.width(width = 200.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    AutoResizeText(
                        text = "Some text using h1 style that should resize to the width",
                        style = MaterialThemeExtended.typography.h1
                    )
                    AutoResizeText(
                        text = "Some text using h2 style that should resize to the width",
                        style = MaterialThemeExtended.typography.h2
                    )
                    AutoResizeText(
                        text = "Some text using h3 style that should resize to the width",
                        style = MaterialThemeExtended.typography.h3
                    )
                    AutoResizeText(
                        text = "Some text using h4 style that should resize to the width",
                        style = MaterialThemeExtended.typography.h4
                    )
                    AutoResizeText(
                        text = "Some using font size 48sp That should resize to the width",
                        fontSize = 48.sp
                    )
                    AutoResizeText(
                        text = "Some using font size 32sp That should resize to the width",
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}