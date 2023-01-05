package com.treefrogapps.androidx.compose.pager

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.runtime.Composable
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

/**
 * Default parallax pager values for effect, mode and fling behaviour
 */
object ParallaxPagerDefaults {

    fun effect(): ParallaxEffect = ParallaxEffect.Small

    fun mode() : ParallaxMode = ParallaxMode.Overlay

    @OptIn(ExperimentalSnapperApi::class)
    @Composable
    fun flingBehavior(state: ParallaxPagerState): FlingBehavior {
        return rememberSnapperFlingBehavior(
            lazyListState = state.listState,
            snapIndex = { _, start, target ->
                when {
                    target > start -> start + 1
                    target < start -> start - 1
                    else           -> target
                }
            })
    }
}