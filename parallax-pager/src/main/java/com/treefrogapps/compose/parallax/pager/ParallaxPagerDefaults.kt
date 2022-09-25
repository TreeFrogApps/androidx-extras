package com.treefrogapps.compose.parallax.pager

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.runtime.Composable
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior

object ParallaxPagerDefaults {

    fun effect(): ParallaxEffect = ParallaxEffect.Small

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