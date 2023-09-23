package com.treefrogapps.androidx.compose.paging

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey

@Composable
internal fun <T : Any> BoxScope.PagingRefreshLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    loadingContent: @Composable BoxScope.() -> Unit,
    loadingErrorContent: @Composable BoxScope.() -> Unit
) {
    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> loadingContent()
        is LoadState.Error   -> loadingErrorContent()
        else                 -> {}
    }
}

@Composable
internal fun BoxScope.PagingAnimatedVisibilityEmptyContent(
    visible: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(animationSpec = tween(durationMillis = 100)),
        content = { content() })
}

internal fun <T : Any> LazyListScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyItemScope.() -> Unit,
    prependErrorContent: @Composable LazyItemScope.() -> Unit
) {
    when (lazyPagingItems.loadState.prepend) {
        is LoadState.Loading -> item { prependLoadingContent(this) }
        is LoadState.Error   -> item { prependErrorContent(this) }
        else                 -> {}
    }
}

internal fun <T : Any> LazyListScope.pagingItemsContent(
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    loadedItemPlaceholderContent: @Composable LazyItemScope.() -> Unit,
    loadedItemContent: @Composable LazyItemScope.(T) -> Unit
) {
    items(
        count = lazyPagingItems.itemCount,
        key = lazyPagingItems.itemKey(key),
    ) { index ->
        val item = lazyPagingItems[index]
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
    when (lazyPagingItems.loadState.append) {
        is LoadState.Loading    -> item { appendLoadingContent(this) }
        is LoadState.Error      -> item { appendErrorContent(this) }
        is LoadState.NotLoading -> {}
    }
}

internal fun <T : Any> LazyGridScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyGridItemScope.() -> Unit,
    prependErrorContent: @Composable LazyGridItemScope.() -> Unit
) {
    when (lazyPagingItems.loadState.prepend) {
        is LoadState.Loading    -> item { prependLoadingContent(this) }
        is LoadState.Error      -> item { prependErrorContent(this) }
        is LoadState.NotLoading -> {}
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
    when (lazyPagingItems.loadState.append) {
        is LoadState.Loading    -> item { appendLoadingContent(this) }
        is LoadState.Error      -> item { appendErrorContent(this) }
        is LoadState.NotLoading -> {}
    }
}

internal fun <T : Any> LazyStaggeredGridScope.pagingPrependLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    prependLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit,
    prependErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit
) {
    when (lazyPagingItems.loadState.prepend) {
        is LoadState.Loading    -> item { prependLoadingContent(this) }
        is LoadState.Error      -> item { prependErrorContent(this) }
        is LoadState.NotLoading -> {}
    }
}

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

internal fun <T : Any> LazyStaggeredGridScope.pagingAppendLoadStateContent(
    lazyPagingItems: LazyPagingItems<T>,
    appendLoadingContent: @Composable LazyStaggeredGridItemScope.() -> Unit,
    appendErrorContent: @Composable LazyStaggeredGridItemScope.() -> Unit
) {
    when (lazyPagingItems.loadState.append) {
        is LoadState.Loading    -> item { appendLoadingContent(this) }
        is LoadState.Error      -> item { appendErrorContent(this) }
        is LoadState.NotLoading -> {}
    }
}

@Composable
internal fun <T : Any> LazyPagingItems<T>.isEmpty(): State<Boolean> =
    remember {
        derivedStateOf {
            with(loadState) {
                append.endOfPaginationReached
                    && prepend.endOfPaginationReached
                    && itemCount == 0
            }
        }
    }

