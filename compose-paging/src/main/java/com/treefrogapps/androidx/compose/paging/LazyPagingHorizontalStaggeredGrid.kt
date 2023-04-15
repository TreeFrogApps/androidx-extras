package com.treefrogapps.androidx.compose.paging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyPagingHorizontalStaggeredGrid(
    modifier: Modifier = Modifier,
    rows: StaggeredGridCells,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalItemSpacing: Dp = 0.dp,
    reverseLayout : Boolean = false,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loadedEmptyContent: @Composable BoxScope.() -> Unit = {},
    refreshLoadingContent: @Composable BoxScope.() -> Unit = {},
    refreshErrorContent: @Composable BoxScope.() -> Unit = {},
    prependLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyStaggeredGridItemScope.(T) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        val emptyVisible by lazyPagingItems.isEmpty()

        LazyHorizontalStaggeredGrid(
            modifier = modifier,
            state = state,
            rows = rows,
            contentPadding = contentPadding,
            horizontalItemSpacing = horizontalItemSpacing,
            verticalArrangement = verticalArrangement,
            reverseLayout = reverseLayout,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnabled
        ) {
            pagingPrependLoadStateContent(
                lazyPagingItems = lazyPagingItems,
                prependLoadingContent = prependLoadingContent,
                prependErrorContent = prependErrorContent)

            pagingItemsContent(
                lazyPagingItems = lazyPagingItems,
                key = key,
                loadedItemContent = loadedItemContent,
                loadedItemPlaceholderContent = loadedItemPlaceholderContent)

            pagingAppendLoadStateContent(
                lazyPagingItems = lazyPagingItems,
                appendLoadingContent = appendLoadingContent,
                appendErrorContent = appendErrorContent)
        }
        PagingAnimatedVisibilityEmptyContent(
            visible = emptyVisible,
            content = loadedEmptyContent)
        PagingRefreshLoadStateContent(
            lazyPagingItems = lazyPagingItems,
            loadingContent = refreshLoadingContent,
            loadingErrorContent = refreshErrorContent)
    }
}

