package com.treefrogapps.androidx.compose.material3.shape

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.treefrogapps.androidx.compose.material.theme.Theme
import com.treefrogapps.androidx.compose.material.theme.WindowSize.Companion.isLandscape

private const val landscapeDrawerWeight = 0.4F
private const val portraitDrawerWeight = 0.8F

class DrawerShape(
    private val widthPixels: Float,
    private val topEndRadius: Dp = 8.dp,
    private val bottomEndRadius: Dp = 8.dp,

    ) : CornerBasedShape(
    topStart = CornerSize(size = 0.dp),
    topEnd = CornerSize(size = topEndRadius),
    bottomStart = CornerSize(size = 0.dp),
    bottomEnd = CornerSize(size = bottomEndRadius)
) {

    override fun copy(topStart: CornerSize, topEnd: CornerSize, bottomEnd: CornerSize, bottomStart: CornerSize): CornerBasedShape =
        DrawerShape(
            widthPixels = widthPixels,
            topEndRadius = topEndRadius,
            bottomEndRadius = bottomEndRadius
        )

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ): Outline =
        if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
            Outline.Rectangle(
                Rect(
                    left = 0f,
                    top = 0f,
                    right = widthPixels,
                    bottom = size.height
                )
            )
        } else {
            Outline.Rounded(
                RoundRect(
                    rect = Rect(
                        left = 0f,
                        top = 0f,
                        right = widthPixels,
                        bottom = size.height
                    ),
                    topLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topStart else topEnd),
                    topRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) topEnd else topStart),
                    bottomRight = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomEnd else bottomStart),
                    bottomLeft = CornerRadius(if (layoutDirection == LayoutDirection.Ltr) bottomStart else bottomEnd)
                )
            )
        }
}

@Composable
fun drawerShape(width: Dp): CornerBasedShape =
    DrawerShape(widthPixels = LocalDensity.current.run { width.toPx() })

@Composable
fun drawerWidth(
    landscapeWeight: Float = landscapeDrawerWeight,
    portraitWeight: Float = portraitDrawerWeight
)
        : Dp = Theme.windowSize.size.width * if (Theme.windowSize.isLandscape()) landscapeWeight else portraitWeight