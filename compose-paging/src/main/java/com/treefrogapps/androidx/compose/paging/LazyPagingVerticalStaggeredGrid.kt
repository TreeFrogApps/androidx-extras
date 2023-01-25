package com.treefrogapps.androidx.compose.paging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : Any> LazyPagingVerticalStaggeredGrid(
    modifier: Modifier = Modifier,
    columns: StaggeredGridCells,
    state: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(0.dp),
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    refreshLoadingContent: @Composable () -> Unit = {},
    refreshErrorContent: @Composable () -> Unit = {},
    prependLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyStaggeredGridItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyStaggeredGridItemScope.(T) -> Unit
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier.fillMaxSize(),
        state = state,
        columns = columns,
        contentPadding = contentPadding,
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

