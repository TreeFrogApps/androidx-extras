package com.treefrogapps.androidx.compose.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.runtime.Composable

/**
 * Default parallax pager values for effect, mode and fling behaviour
 */
object ParallaxPagerDefaults {

    fun effect(): ParallaxEffect = ParallaxEffect.Small

    fun mode(): ParallaxMode = ParallaxMode.Overlay

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun flingBehavior(state: ParallaxPagerState): FlingBehavior =
        rememberSnapFlingBehavior(lazyListState = state.listState)
}