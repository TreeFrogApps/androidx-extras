package com.treefrogapps.androidx.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyPagingHorizontalStaggeredGrid(
    modifier: Modifier = Modifier,
    rows: StaggeredGridCells,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    refreshLoadingContent: @Composable () -> Unit = {},
    refreshErrorContent: @Composable () -> Unit = {},
    prependLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyStaggeredGridItemScope.(T) -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LazyHorizontalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            state = state,
            rows = rows
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

        PagingRefreshLoadStateContent(
            lazyPagingItems = lazyPagingItems,
            loadingContent = refreshLoadingContent,
            loadingErrorContent = refreshErrorContent)
    }

}

