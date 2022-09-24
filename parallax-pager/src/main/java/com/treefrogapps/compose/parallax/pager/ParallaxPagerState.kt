package com.treefrogapps.compose.parallax.pager

import androidx.annotation.IntRange
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.ScrollScope
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
    @IntRange(from = 0) initialPage: Int = 0
) : ScrollableState {

    internal companion object {
        val Saver: Saver<ParallaxPagerState, Int> = object : Saver<ParallaxPagerState, Int> {
            override fun restore(value: Int): ParallaxPagerState = ParallaxPagerState(initialPage = value)
            override fun SaverScope.save(value: ParallaxPagerState): Int = value.currentPage.value
        }
    }

    internal val wrappedState: LazyListState = LazyListState(firstVisibleItemIndex = initialPage)

    internal val mostVisiblePageLayoutInfo: State<LazyListItemInfo?> by lazy(NONE) {
        derivedStateOf {
            val layoutInfo = wrappedState.layoutInfo
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

    internal val currentPageBackgroundOffset: State<Int> by lazy(NONE) {
        derivedStateOf { currentPageLayoutInfo?.offset ?: 0 }
    }

    internal val currentPageForegroundOffset: State<Int> by lazy(NONE) {
        derivedStateOf { (currentPageBackgroundOffset.value * 1.2).toInt() }
    }

    internal val currentPage: State<Int> by lazy(NONE) {
        derivedStateOf { mostVisiblePageLayoutInfo.value?.index ?: 0 }
    }

    internal val currentPageLayoutInfo: LazyListItemInfo?
        get() = wrappedState
            .layoutInfo
            .visibleItemsInfo
            .lastOrNull { info -> info.index == currentPage.value }

    internal fun pageInfoForPage(page: Int): LazyListItemInfo? =
        wrappedState
            .layoutInfo
            .visibleItemsInfo
            .find { info -> info.index == page }

    internal fun parallaxOffsetForPage(page: Int, effect: ParallaxEffect): State<Int> = derivedStateOf {
        ((pageInfoForPage(page = page)?.offset ?: 0) * effect.amount).toInt()
    }

    override val isScrollInProgress: Boolean =
        wrappedState.isScrollInProgress

    override fun dispatchRawDelta(delta: Float): Float =
        wrappedState.dispatchRawDelta(delta = delta)

    override suspend fun scroll(scrollPriority: MutatePriority, block: suspend ScrollScope.() -> Unit) =
        wrappedState.scroll(scrollPriority = scrollPriority, block = block)

    suspend fun scrollToPage(page: Int) {
        wrappedState.scrollToItem(index = page)
    }
}