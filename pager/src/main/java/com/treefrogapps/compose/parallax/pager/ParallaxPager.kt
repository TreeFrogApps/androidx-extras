package com.treefrogapps.compose.parallax.pager

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun rememberParallaxPagerState(): ParallaxPagerState = rememberSaveable(saver = ParallaxPagerState.Saver) {
    ParallaxPagerState(initialPage = 0)
}

@Composable
fun HorizontalParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState = rememberParallaxPagerState(),
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    flingBehavior: FlingBehavior = ParallaxPagerDefaults.flingBehavior(state = state),
    effect: ParallaxEffect = ParallaxPagerDefaults.effect(),
    mode: ParallaxMode = ParallaxPagerDefaults.mode(),
    userScrollEnabled: Boolean = true,
    pages: List<ParallaxPage>,
) {
    ParallaxPager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        effect = effect,
        mode = mode,
        userScrollEnabled = userScrollEnabled,
        isVertical = false,
        pages = pages
    )
}

@Composable
fun VerticalParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState = rememberParallaxPagerState(),
    contentPadding: PaddingValues = PaddingValues(all = 0.dp),
    flingBehavior: FlingBehavior = ParallaxPagerDefaults.flingBehavior(state = state),
    effect: ParallaxEffect = ParallaxPagerDefaults.effect(),
    mode: ParallaxMode = ParallaxPagerDefaults.mode(),
    userScrollEnabled: Boolean = true,
    pages: List<ParallaxPage>
) {
    ParallaxPager(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        flingBehavior = flingBehavior,
        effect = effect,
        mode = mode,
        userScrollEnabled = userScrollEnabled,
        isVertical = true,
        pages = pages
    )
}

@Composable
internal fun ParallaxPager(
    modifier: Modifier = Modifier,
    state: ParallaxPagerState,
    contentPadding: PaddingValues,
    flingBehavior: FlingBehavior,
    effect: ParallaxEffect,
    mode: ParallaxMode,
    userScrollEnabled: Boolean,
    pages: List<ParallaxPage>,
    isVertical: Boolean,
) {
    val pagerScope = remember(state) { ParallaxPagerScope(state) }

    if (isVertical) {
        LazyColumn(
            modifier = modifier,
            state = state.listState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = {
                parallaxItems(
                    state = state,
                    effect = effect,
                    mode = mode,
                    pagerScope = pagerScope,
                    pages = pages,
                    isVertical = true
                )
            })
    } else {
        LazyRow(
            modifier = modifier,
            state = state.listState,
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled,
            content = {
                parallaxItems(
                    state = state,
                    effect = effect,
                    mode = mode,
                    pagerScope = pagerScope,
                    pages = pages,
                    isVertical = false
                )
            })
    }
}

private fun LazyListScope.parallaxItems(
    state: ParallaxPagerState,
    effect: ParallaxEffect,
    mode: ParallaxMode,
    pagerScope: ParallaxPagerScope,
    pages: List<ParallaxPage>,
    isVertical: Boolean
) {
    when (mode) {
        ParallaxMode.Overlay -> overlayParallaxItems(
            state = state,
            effect = effect,
            pagerScope = pagerScope,
            pages = pages,
            isVertical = isVertical
        )
        ParallaxMode.Stacked -> stackedParallaxItems(
            state = state,
            effect = effect,
            pagerScope = pagerScope,
            pages = pages,
            isVertical = isVertical
        )
        ParallaxMode.Aligned -> alignedParallaxItems(
            state = state,
            effect = effect,
            pagerScope = pagerScope,
            pages = pages,
            isVertical = isVertical
        )
    }
}

private fun LazyListScope.overlayParallaxItems(
    state: ParallaxPagerState,
    effect: ParallaxEffect,
    pagerScope: ParallaxPagerScope,
    pages: List<ParallaxPage>,
    isVertical: Boolean
) {
    items(
        count = pages.size,
        key = { it },
        itemContent = { pageIdx ->
            Column(
                modifier = Modifier.fillParentMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val page = pages[pageIdx]
                page.header?.invoke(pagerScope, pageIdx)
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    page.content.invoke(pagerScope, pageIdx)
                    page.layers.forEachIndexed { layerIdx, layer ->
                        val pageParallaxOffset by remember(pageIdx) {
                            state.parallaxOffsetForPage(
                                page = pageIdx,
                                effect = effect,
                                multiplier = layerIdx + 1
                            )
                        }
                        Box(
                            modifier = Modifier.parallaxOffset(isVertical = isVertical, pageParallaxOffset),
                            contentAlignment = Alignment.Center
                        ) {
                            layer.invoke(pagerScope, pageIdx, layerIdx)
                        }
                    }
                }
                page.footer?.invoke(pagerScope, pageIdx)
            }
        })
}

private fun LazyListScope.stackedParallaxItems(
    state: ParallaxPagerState,
    effect: ParallaxEffect,
    pagerScope: ParallaxPagerScope,
    pages: List<ParallaxPage>,
    isVertical: Boolean
) {
    items(
        count = pages.size,
        key = { it },
        itemContent = { pageIdx ->
            Column(
                modifier = Modifier.fillParentMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val page = pages[pageIdx]
                page.header?.invoke(pagerScope, pageIdx)
                if (isVertical) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        page.content.invoke(pagerScope, pageIdx)
                        page.layers.forEachIndexed { layerIdx, layer ->
                            val pageParallaxOffset by remember(pageIdx) {
                                state.parallaxOffsetForPage(
                                    page = pageIdx,
                                    effect = effect,
                                    multiplier = layerIdx + 1
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .parallaxOffset(
                                        isVertical = true,
                                        offset = pageParallaxOffset
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                layer.invoke(pagerScope, pageIdx, layerIdx)
                            }
                        }
                    }
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        page.content.invoke(pagerScope, pageIdx)
                        page.layers.forEachIndexed { layerIdx, layer ->
                            val pageParallaxOffset by remember(pageIdx) {
                                state.parallaxOffsetForPage(
                                    page = pageIdx,
                                    effect = effect,
                                    multiplier = layerIdx + 1
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .parallaxOffset(
                                        isVertical = false,
                                        offset = pageParallaxOffset
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                layer.invoke(pagerScope, pageIdx, layerIdx)
                            }
                        }
                    }
                }
                page.footer?.invoke(pagerScope, pageIdx)
            }
        })
}

private fun LazyListScope.alignedParallaxItems(
    state: ParallaxPagerState,
    effect: ParallaxEffect,
    pagerScope: ParallaxPagerScope,
    pages: List<ParallaxPage>,
    isVertical: Boolean
) {
    items(
        count = pages.size,
        key = { it },
        itemContent = { pageIdx ->
            Column(
                modifier = Modifier.fillParentMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val page = pages[pageIdx]
                page.header?.invoke(pagerScope, pageIdx)
                Box {
                    Box(
                        modifier = Modifier.wrapContentSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        page.content.invoke(pagerScope, pageIdx)
                        page.layers.forEachIndexed { layerIdx, layer ->
                            val pageParallaxOffset by remember(pageIdx) {
                                state.parallaxOffsetForPage(
                                    page = pageIdx,
                                    effect = effect,
                                    multiplier = layerIdx + 1
                                )
                            }
                            Box(
                                modifier = Modifier.parallaxOffset(isVertical = isVertical, pageParallaxOffset),
                                contentAlignment = Alignment.Center
                            ) {
                                layer.invoke(pagerScope, pageIdx, layerIdx)
                            }
                        }
                    }
                }
                page.footer?.invoke(pagerScope, pageIdx)
            }
        })
}

private fun Modifier.parallaxOffset(isVertical: Boolean, offset: Int): Modifier =
    this.then(Modifier.run { if (isVertical) offset(y = offset.dp) else offset(x = offset.dp) })


