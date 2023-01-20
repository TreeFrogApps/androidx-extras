package com.treefrogapps.androidx.compose.paging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

@Composable
internal fun <T : Any> PagingRefreshLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    loadingContent: @Composable () -> Unit,
    loadingErrorContent: @Composable () -> Unit
) {
    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Loading    -> loadingContent()
        is LoadState.Error      -> loadingErrorContent()
        is LoadState.NotLoading -> {}
    }
}

internal fun <T : Any> LazyListScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyItemScope.() -> Unit,
    prependErrorContent: @Composable LazyItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.prepend) {
            is LoadState.Loading    -> prependLoadingContent(this)
            is LoadState.Error      -> prependErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

internal fun <T : Any> LazyListScope.pagingItemsContent(
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    loadedItemPlaceholderContent: @Composable LazyItemScope.() -> Unit,
    loadedItemContent: @Composable LazyItemScope.(T) -> Unit
) {
    items(
        items = lazyPagingItems,
        key = key
    ) { item ->
        if (item != null) {
            loadedItemContent(this, item)
        } else loadedItemPlaceholderContent(this)
    }
}

internal fun <T : Any> LazyListScope.pagingAppendLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    appendLoadingContent: @Composable LazyItemScope.() -> Unit,
    appendErrorContent: @Composable LazyItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading    -> appendLoadingContent(this)
            is LoadState.Error      -> appendErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

internal fun <T : Any> LazyGridScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyGridItemScope.() -> Unit,
    prependErrorContent: @Composable LazyGridItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.prepend) {
            is LoadState.Loading    -> prependLoadingContent(this)
            is LoadState.Error      -> prependErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

internal fun <T : Any> LazyGridScope.pagingItemsContent(
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    loadedItemPlaceholderContent: @Composable LazyGridItemScope.() -> Unit,
    loadedItemContent: @Composable LazyGridItemScope.(T) -> Unit
) {
    items(
        items = lazyPagingItems,
        key = key
    ) { item ->
        if (item != null) {
            loadedItemContent(this, item)
        } else loadedItemPlaceholderContent(this)
    }
}

internal fun <T : Any> LazyGridScope.pagingAppendLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    appendLoadingContent: @Composable LazyGridItemScope.() -> Unit,
    appendErrorContent: @Composable LazyGridItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading    -> appendLoadingContent(this)
            is LoadState.Error      -> appendErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
internal fun <T : Any> LazyStaggeredGridScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit,
    prependErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.prepend) {
            is LoadState.Loading    -> prependLoadingContent(this)
            is LoadState.Error      -> prependErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
internal fun <T : Any> LazyStaggeredGridScope.pagingItemsContent(
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    loadedItemPlaceholderContent: @Composable LazyStaggeredGridItemScope.() -> Unit,
    loadedItemContent: @Composable LazyStaggeredGridItemScope.(T) -> Unit
) {
    items(
        items = lazyPagingItems,
        key = key
    ) { item ->
        if (item != null) {
            loadedItemContent(this, item)
        } else loadedItemPlaceholderContent(this)
    }
}

@OptIn(ExperimentalFoundationApi::class)
internal fun <T : Any> LazyStaggeredGridScope.pagingAppendLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    appendLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit,
    appendErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit
) {
    item {
        when (lazyPagingItems.loadState.append) {
            is LoadState.Loading    -> appendLoadingContent(this)
            is LoadState.Error      -> appendErrorContent(this)
            is LoadState.NotLoading -> {}
        }
    }
}

