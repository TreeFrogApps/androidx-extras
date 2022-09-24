package com.treefrogapps.compose.parallax.pager

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

object ParallaxPagerDefaults {

    fun effect(): ParallaxEffect = ParallaxEffect.Subtle

    @Composable
    fun flingBehavior(): FlingBehavior {
        val flingSpec = rememberSplineBasedDecay<Float>()
        return remember(flingSpec) {
            ParallaxFlingBehavior(flingSpec)
        }
    }
}