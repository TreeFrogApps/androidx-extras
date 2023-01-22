package com.treefrogapps.androidx.compose.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import com.treefrogapps.androidx.compose.ui.graphics.linearVerticalGradient
import kotlinx.coroutines.launch

fun Modifier.maxWeight(rowScope: RowScope): Modifier =
    with(rowScope) {
        then(Modifier.weight(weight = 1.0F))
    }

fun Modifier.maxWeight(columnScope: ColumnScope): Modifier =
    with(columnScope) {
        then(Modifier.weight(weight = 1.0F))
    }

fun Modifier.heightPercent(percent: Float = 1.0F, height: Dp): Modifier =
    then(Modifier.height(height = height.times(percent)))

fun Modifier.verticalScroll(): Modifier = composed {
    then(Modifier.verticalScroll(state = rememberScrollState()))
}

fun Modifier.horizontalScroll(): Modifier = composed {
    then(Modifier.horizontalScroll(state = rememberScrollState()))
}

fun Modifier.verticalGradientBackground(
    start: Color,
    end: Color,
    shape: Shape = RectangleShape
): Modifier = this.then(
    other = Modifier.background(
        brush = Brush.linearVerticalGradient(
            start = start,
            end = end),
        shape = shape)
)

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bringIntoViewWhenFocused(): Modifier = composed {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    this.then(Modifier
        .onFocusChanged { fs ->
            if (fs.isFocused) coroutineScope.launch { bringIntoViewRequester.bringIntoView() }
        }
        .bringIntoViewRequester(bringIntoViewRequester))
}

fun Modifier.verticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp,
    color: Color = Color.LightGray,
    xOffset: Dp = 8.dp
): Modifier = composed {
    val targetAlpha by remember { derivedStateOf { if (state.isScrollInProgress) 1f else 0f } }
    val duration by remember { derivedStateOf { if (state.isScrollInProgress) 150 else 500 } }
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration))

    then(drawWithContent {
        drawContent()

        val firstVisibleIndex: Int by derivedStateOf { state.firstVisibleItemIndex }
        val needDrawScrollbar by derivedStateOf { state.isScrollInProgress || alpha > 0.0f }
        if (needDrawScrollbar) {
            val elementHeight = size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = (firstVisibleIndex * elementHeight)
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight
            drawRoundRect(
                color = color,
                topLeft = Offset(x = size.width + xOffset.toPx(), y = scrollbarOffsetY),
                cornerRadius = CornerRadius(x = width.toPx(), y = width.toPx()),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha)
        }
    })
}




