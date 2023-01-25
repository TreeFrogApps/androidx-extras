package com.treefrogapps.androidx.compose.paging

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingHorizontalGrid(
    modifier: Modifier = Modifier,
    rows: GridCells,
    state: LazyGridState = rememberLazyGridState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    refreshLoadingContent: @Composable () -> Unit = {},
    refreshErrorContent: @Composable () -> Unit = {},
    prependLoadingContent: @Composable LazyGridItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyGridItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyGridItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyGridItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyGridItemScope.(T) -> Unit
) {
    LazyHorizontalGrid(
        modifier = modifier,
        state = state,
        rows = rows,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
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

    PagingRefreshLoadStateContent(
        lazyPagingItems = lazyPagingItems,
        loadingContent = refreshLoadingContent,
        loadingErrorContent = refreshErrorContent)
}

