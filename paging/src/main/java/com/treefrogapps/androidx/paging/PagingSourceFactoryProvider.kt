package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Helper function to act as a provider factory for a [PagingSource] and directly
 * delegating [refreshKey] and [load] parameters to the [PagingSource].
 */
fun <K : Any, V : Any> pagingSourceFactoryProvider(
    refreshKey: (state: PagingState<K, V>) -> K?,
    load: suspend (params: PagingSource.LoadParams<K>) -> PagingSource.LoadResult<K, V>,
    loadContext: CoroutineContext = Dispatchers.IO
): () -> PagingSource<K, V> = {
    pagingSource(
        refreshKey = refreshKey,
        load = load,
        loadContext = loadContext
    )
}


