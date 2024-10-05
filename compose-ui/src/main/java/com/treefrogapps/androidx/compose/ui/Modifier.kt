package com.treefrogapps.androidx.compose.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.treefrogapps.androidx.compose.ui.graphics.LocalShimmerTheme
import com.treefrogapps.androidx.compose.ui.graphics.ShimmerTheme
import com.treefrogapps.androidx.compose.ui.graphics.linearVerticalGradient
import com.treefrogapps.androidx.compose.ui.graphics.shimmerBrush
import kotlinx.coroutines.launch

fun Modifier.maxWeight(rowScope: RowScope): Modifier =
    this then with(rowScope) {
        Modifier.weight(weight = 1.0f)
    }

fun Modifier.maxWeight(columnScope: ColumnScope): Modifier =
    this then with(columnScope) {
        Modifier.weight(weight = 1.0f)
    }

fun Modifier.maxZ(): Modifier =
    this then Modifier.zIndex(zIndex = 1f)

fun Modifier.minZ(): Modifier =
    this then Modifier.zIndex(zIndex = 0f)

fun Modifier.heightPercent(percent: Float = 1.0f, height: Dp): Modifier =
    this then Modifier.height(height = height.times(percent))

@Composable
fun Modifier.verticalScroll(): Modifier =
    this then Modifier.verticalScroll(state = rememberScrollState())

@Composable
fun Modifier.horizontalScroll(): Modifier =
    this then Modifier.horizontalScroll(state = rememberScrollState())

fun Modifier.verticalGradientBackground(
    start: Color,
    end: Color,
    shape: Shape = RectangleShape
): Modifier = this then
        Modifier.background(
            brush = Brush.linearVerticalGradient(
                start = start,
                end = end
            ),
            shape = shape
        )

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bringIntoViewWhenFocused(): Modifier {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
    return this then Modifier
        .onFocusChanged { fs ->
            if (fs.isFocused) coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
        }
        .bringIntoViewRequester(bringIntoViewRequester)
}

@Composable
fun Modifier.verticalListScrollbar(
    state: LazyListState,
    width: Dp = 8.dp,
    color: Color = Color.LightGray,
    xOffset: Dp = 8.dp
): Modifier {
    val targetAlpha by remember { derivedStateOf { if (state.isScrollInProgress) 1f else 0f } }
    val duration by remember { derivedStateOf { if (state.isScrollInProgress) 150 else 500 } }
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration),
        label = "VerticalScrollbarFloatAnimation"
    )
    val firstVisibleItemIndex by remember { derivedStateOf { state.firstVisibleItemIndex } }
    val needDrawScrollbar by remember { derivedStateOf { state.isScrollInProgress || alpha > 0f } }
    val totalItemsCount by remember { derivedStateOf { state.layoutInfo.totalItemsCount } }
    val visibleItemsSize by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo.size } }

    return verticalScrollBar(
        width = width,
        color = color,
        xOffset = xOffset,
        firstVisibleItemIndex = firstVisibleItemIndex,
        needDrawScrollbar = needDrawScrollbar,
        totalItemsCount = totalItemsCount,
        visibleItemsSize = visibleItemsSize,
        alpha = alpha
    )
}

@Composable
fun Modifier.verticalGridScrollbar(
    state: LazyGridState,
    width: Dp = 8.dp,
    color: Color = Color.LightGray,
    xOffset: Dp = 8.dp
): Modifier {
    val targetAlpha by remember { derivedStateOf { if (state.isScrollInProgress) 1f else 0f } }
    val duration by remember { derivedStateOf { if (state.isScrollInProgress) 150 else 500 } }
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration),
        label = "VerticalScrollbarFloatAnimation"
    )
    val firstVisibleItemIndex by remember { derivedStateOf { state.firstVisibleItemIndex } }
    val needDrawScrollbar by remember { derivedStateOf { state.isScrollInProgress || alpha > 0f } }
    val totalItemsCount by remember { derivedStateOf { state.layoutInfo.totalItemsCount } }
    val visibleItemsSize by remember { derivedStateOf { state.layoutInfo.visibleItemsInfo.size } }

    return verticalScrollBar(
        width = width,
        color = color,
        xOffset = xOffset,
        firstVisibleItemIndex = firstVisibleItemIndex,
        needDrawScrollbar = needDrawScrollbar,
        totalItemsCount = totalItemsCount,
        visibleItemsSize = visibleItemsSize,
        alpha = alpha
    )
}

@Composable
private fun Modifier.verticalScrollBar(
    width: Dp,
    color: Color,
    xOffset: Dp,
    firstVisibleItemIndex: Int,
    needDrawScrollbar: Boolean,
    totalItemsCount: Int,
    visibleItemsSize: Int,
    alpha: Float
): Modifier = this then drawWithContent {
    drawContent()
    if (needDrawScrollbar) {
        val elementHeight = size.height / totalItemsCount
        val scrollbarOffsetY = (firstVisibleItemIndex * elementHeight)
        val scrollbarHeight = visibleItemsSize * elementHeight
        drawRoundRect(
            color = color,
            topLeft = Offset(x = size.width + xOffset.toPx(), y = scrollbarOffsetY),
            cornerRadius = CornerRadius(x = width.toPx(), y = width.toPx()),
            size = Size(width.toPx(), scrollbarHeight),
            alpha = alpha
        )
    }
}

@Composable
fun Modifier.shimmerBackground(
    enabled: Boolean = true,
    shimmerTheme: ShimmerTheme = LocalShimmerTheme.current
): Modifier =
    this then Modifier.background(
        brush = shimmerBrush(
            enabled = enabled,
            shimmerTheme = shimmerTheme
        )
    )

@Composable
fun Modifier.shimmerForeground(
    enabled: Boolean = true,
    shimmerTheme: ShimmerTheme = LocalShimmerTheme.current
): Modifier {
    val shimmerBrush = shimmerBrush(
        enabled = enabled,
        shimmerTheme = shimmerTheme
    )
    return this then Modifier.drawWithContent {
        drawContent()
        drawRect(brush = shimmerBrush)
    }
}
