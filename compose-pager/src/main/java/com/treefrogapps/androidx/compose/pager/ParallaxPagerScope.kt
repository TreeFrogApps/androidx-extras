package com.treefrogapps.androidx.compose.pager

import androidx.compose.runtime.State

class ParallaxPagerScope internal constructor(
    private val state: ParallaxPagerState,
) {
    fun currentPagePixelOffset(): State<Int> = state.currentPagePixelOffset
    fun currentPageRatioOffset(): State<Float> = state.currentPageRatioOffset
}