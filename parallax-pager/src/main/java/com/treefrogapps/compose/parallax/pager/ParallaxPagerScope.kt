package com.treefrogapps.compose.parallax.pager

import androidx.compose.runtime.State

class ParallaxPagerScope internal constructor(
    private val state: ParallaxPagerState,
) {
    fun currentPageOffset(): State<Int> = state.currentPageOffset
}