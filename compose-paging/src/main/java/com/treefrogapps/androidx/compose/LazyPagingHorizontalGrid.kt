package com.treefrogapps.androidx.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingHorizontalGrid(
    modifier: Modifier = Modifier,
    rows: GridCells,
    state: LazyGridState = rememberLazyGridState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    refreshLoadingContent: @Composable () -> Unit = {},
    refreshErrorContent: @Composable () -> Unit = {},
    prependLoadingContent: @Composable LazyGridItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyGridItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyGridItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyGridItemScope.(T) -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LazyHorizontalGrid(
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

