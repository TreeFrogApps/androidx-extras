package com.treefrogapps.compose.parallax.pager

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalParallaxPageIndicator(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState,
    pageCount: Int,
    pageIndicatorColor: Color,
    activePageIndicatorColor: Color
) {
    ParallaxPagerIndicator(
        modifier = modifier,
        state = state,
        pageCount = pageCount,
        pageIndicatorColor = pageIndicatorColor,
        activePageIndicatorColor = activePageIndicatorColor,
        isVertical = false
    )
}

@Composable
fun VerticalParallaxPageIndicator(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState,
    pageCount: Int,
    pageIndicatorColor: Color,
    activePageIndicatorColor: Color
) {
    ParallaxPagerIndicator(
        modifier = modifier,
        state = state,
        pageCount = pageCount,
        pageIndicatorColor = pageIndicatorColor,
        activePageIndicatorColor = activePageIndicatorColor,
        isVertical = true
    )
}


@Composable
private fun ParallaxPagerIndicator(
    modifier: Modifier,
    state: ParallaxPagerState,
    pageCount: Int,
    pageIndicatorColor: Color,
    activePageIndicatorColor: Color,
    isVertical: Boolean
) {
    Box(modifier = modifier) {
        if (isVertical) {
            Column(
                modifier = Modifier
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val activePageIdx by remember(state) { state.currentPage }

                (0 until pageCount).forEach { pageIdx ->
                    PageIndicator(
                        pageIdx = pageIdx,
                        activePageIdx = activePageIdx,
                        pageIndicatorColor = pageIndicatorColor,
                        activePageIndicatorColor = activePageIndicatorColor
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val activePageIdx by remember(state) { state.currentPage }

                (0 until pageCount).forEach { pageIdx ->
                    PageIndicator(
                        pageIdx = pageIdx,
                        activePageIdx = activePageIdx,
                        pageIndicatorColor = pageIndicatorColor,
                        activePageIndicatorColor = activePageIndicatorColor
                    )
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(
    pageIdx: Int,
    activePageIdx: Int,
    pageIndicatorColor: Color,
    activePageIndicatorColor: Color
) {
    Canvas(
        modifier = Modifier
            .size(size = if (pageIdx == activePageIdx) 16.dp else 14.dp)
            .padding(all = 4.dp)
    ) {
        drawCircle(
            color = if (pageIdx == activePageIdx) activePageIndicatorColor else pageIndicatorColor,
            center = Offset(size.width / 2, size.height / 2),
            style = Fill
        )
    }
}

@Preview
@Composable
private fun HorizontalParallaxPageIndicatorPreview() {
    HorizontalParallaxPageIndicator(
        state = rememberParallaxPagerState(),
        pageCount = 6,
        pageIndicatorColor = Color.Black.copy(alpha = 0.2F),
        activePageIndicatorColor = Color.Blue.copy(alpha = 0.4F)
    )
}

@Preview
@Composable
private fun VerticalParallaxPageIndicatorPreview() {
    VerticalParallaxPageIndicator(
        state = rememberParallaxPagerState(),
        pageCount = 6,
        pageIndicatorColor = Color.Black.copy(alpha = 0.2F),
        activePageIndicatorColor = Color.Blue.copy(alpha = 0.4F)
    )
}