package com.treefrogapps.androidx.compose.paging

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingRow(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    lazyPagingItems: LazyPagingItems<T>,
    key: ((T) -> Any)? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout) Arrangement.Start else Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    loadedEmptyContent: @Composable BoxScope.() -> Unit = {},
    refreshLoadingContent: @Composable BoxScope.() -> Unit = {},
    refreshErrorContent: @Composable BoxScope.() -> Unit = {},
    prependLoadingContent: @Composable LazyItemScope.() -> Unit = {},
    prependErrorContent: @Composable LazyItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyItemScope.() -> Unit = {},
    appendErrorContent: @Composable LazyItemScope.() -> Unit = {},
    loadedItemPlaceholderContent: @Composable LazyItemScope.() -> Unit = {},
    loadedItemContent: @Composable LazyItemScope.(T) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = lazyPagingItems.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut(animationSpec = tween(durationMillis = 100)),
            content = { loadedEmptyContent() })
        LazyRow(
            state = state,
            modifier = modifier,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
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
}