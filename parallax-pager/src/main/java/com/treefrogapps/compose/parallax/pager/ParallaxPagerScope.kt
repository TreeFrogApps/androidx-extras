package com.treefrogapps.compose.parallax.pager

import androidx.compose.runtime.State

class ParallaxPagerScope internal constructor(
    private val state: ParallaxPagerState,
) {
    fun currentPage(): State<Int> = state.currentPage
    fun currentPageBackgroundOffset(): State<Int> = state.currentPageBackgroundOffset
    fun currentPageForegroundOffset(): State<Int> = state.currentPageForegroundOffset
}