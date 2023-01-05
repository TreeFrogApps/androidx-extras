package com.treefrogapps.androidx.compose.pager

import androidx.annotation.IntRange
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import kotlin.LazyThreadSafetyMode.NONE

@Stable
class ParallaxPagerState internal constructor(
    @IntRange(from = 0) initialPage: Int = 0,
    val listState: LazyListState = LazyListState(firstVisibleItemIndex = initialPage)
) : ScrollableState by listState {

    internal companion object {
        val Saver: Saver<ParallaxPagerState, Int> = object : Saver<ParallaxPagerState, Int> {
            override fun restore(value: Int): ParallaxPagerState = ParallaxPagerState(initialPage = value)
            override fun SaverScope.save(value: ParallaxPagerState): Int = value.currentPage.value
        }
    }

    private val mostVisiblePageLayoutInfo: State<LazyListItemInfo?> by lazy(NONE) {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            layoutInfo.visibleItemsInfo.maxByOrNull {
                val start = maxOf(it.offset, 0)
                val end = minOf(
                    it.offset + it.size,
                    layoutInfo.viewportEndOffset - layoutInfo.afterContentPadding
                )
                end - start
            }
        }
    }

    private val currentPageLayoutInfo: LazyListItemInfo?
        get() = listState
            .layoutInfo
            .visibleItemsInfo
            .lastOrNull { info -> info.index == currentPage.value }

    private fun pageInfoForPage(page: Int): LazyListItemInfo? =
        listState
            .layoutInfo
            .visibleItemsInfo
            .find { info -> info.index == page }

    internal fun parallaxOffsetForPage(
        page: Int,
        effect: ParallaxEffect,
        multiplier: Int = 1
    ): State<Int> = derivedStateOf {
        ((pageInfoForPage(page = page)?.offset ?: 0) * effect.amount * multiplier).toInt()
    }

    val currentPagePixelOffset: State<Int> by lazy(NONE) {
        derivedStateOf { currentPageLayoutInfo?.offset ?: 0 }
    }

    val currentPageRatioOffset: State<Float> by lazy(NONE) {
        derivedStateOf {
            currentPageLayoutInfo?.let {
                (-it.offset / (it.size).toFloat()).coerceIn(-0.5f, 0.5f)
            } ?: 0f
        }
    }

    val currentPage: State<Int> by lazy(NONE) {
        derivedStateOf { mostVisiblePageLayoutInfo.value?.index ?: 0 }
    }
}