package com.treefrogapps.compose.parallax.pager

import androidx.annotation.IntRange
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.abs


@Composable
fun rememberParallaxPagerState(): ParallaxPagerState = rememberSaveable(saver = ParallaxPagerState.Saver) {
    ParallaxPagerState(initialPage = 0)
}

@Composable
fun HorizontalParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState = rememberParallaxPagerState(),
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    flingBehavior: FlingBehavior = ParallaxPagerDefaults.flingBehavior(),
    effect: ParallaxEffect = ParallaxPagerDefaults.effect(),
    userScrollEnabled: Boolean = true,
    @IntRange(from = 0) pageCount: Int,
    backgroundContent: @Composable PagerScope.(page: Int) -> Unit,
    foregroundContent: @Composable PagerScope.(page: Int) -> Unit
) {
    ParallaxPager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        effect = effect,
        userScrollEnabled = userScrollEnabled,
        pageCount = pageCount,
        isVertical = false,
        backgroundContent = backgroundContent,
        foregroundContent = foregroundContent
    )
}

@Composable
fun VerticalParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState = rememberParallaxPagerState(),
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    flingBehavior: FlingBehavior = ParallaxPagerDefaults.flingBehavior(),
    effect: ParallaxEffect = ParallaxPagerDefaults.effect(),
    userScrollEnabled: Boolean = true,
    @IntRange(from = 0) pageCount: Int,
    backgroundContent: @Composable PagerScope.(page: Int) -> Unit,
    foregroundContent: @Composable PagerScope.(page: Int) -> Unit
) {
    ParallaxPager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        effect = effect,
        userScrollEnabled = userScrollEnabled,
        pageCount = pageCount,
        isVertical = true,
        backgroundContent = backgroundContent,
        foregroundContent = foregroundContent
    )
}

@Composable
internal fun ParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState,
    contentPadding: PaddingValues,
    flingBehavior: FlingBehavior,
    effect: ParallaxEffect,
    userScrollEnabled: Boolean = true,
    @IntRange(from = 0) pageCount: Int,
    isVertical: Boolean,

    backgroundContent: @Composable PagerScope.(page: Int) -> Unit,
    foregroundContent: @Composable PagerScope.(page: Int) -> Unit
) {

    val pagerScope = remember(state, effect) { PagerScope(state, effect) }

    if (isVertical) {
        LazyColumn(
            modifier = modifier,
            state = state.wrappedState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled, content = {
                items(count = pageCount, key = { it }, itemContent = { idx ->
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        pagerScope.backgroundContent(idx)
                        pagerScope.foregroundContent(idx)
                    }
                })
            })
    } else {
        LazyRow(
            modifier = modifier,
            state = state.wrappedState,
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled, content = {
                items(
                    count = pageCount,
                    key = { it },
                    itemContent = { idx ->
                        val pageParallaxOffset by remember(idx) { state.parallaxOffsetForPage(page = idx, effect = effect) }
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            pagerScope.backgroundContent(idx)
                            Box(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .offset(x = pageParallaxOffset.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                pagerScope.foregroundContent(idx)
                            }
                        }
                    })
            })
    }
}

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


class PagerScope internal constructor(
    private val state: ParallaxPagerState,
    private val effect: ParallaxEffect,
) {
    fun currentPage(): State<Int> = state.currentPage
    fun currentPageBackgroundOffset(): State<Int> = state.currentPageBackgroundOffset
    fun currentPageForegroundOffset(): State<Int> = state.currentPageForegroundOffset
    fun parallaxOffsetForPage(page: Int): State<Int> = state.parallaxOffsetForPage(page, effect)
}

private class ParallaxFlingBehavior(
    private val flingDecay: DecayAnimationSpec<Float>
) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        // come up with the better threshold, but we need it since spline curve gives us NaNs
        return if (abs(initialVelocity) > 1f) {
            var velocityLeft = initialVelocity
            var lastValue = 0f
            AnimationState(
                initialValue = 0f,
                initialVelocity = initialVelocity,
            ).animateDecay(flingDecay) {
                val delta = value - lastValue
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
            velocityLeft
        } else {
            initialVelocity
        }
    }
}
