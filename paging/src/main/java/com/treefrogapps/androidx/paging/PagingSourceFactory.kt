package com.treefrogapps.androidx.paging

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Helper function to act as a provider factory for a [PagingSource] and directly
 * delegating [refreshKey] and [load] parameters to the [PagingSource].
 *
 * @param Key
 * @param Value
 * @param refreshKey
 * @param load
 * @param loadContext
 * @receiver
 * @receiver
 * @return
 */
inline fun <Key : Any, Value : Any> pagingSourceFactory(
    crossinline refreshKey: (state: PagingState<Key, Value>) -> Key?,
    crossinline load: suspend (params: PagingSource.LoadParams<Key>) -> PagingSource.LoadResult<Key, Value>,
    loadContext: CoroutineContext = Dispatchers.IO
): PagingSourceFactory<Key, Value> =
    PagingSourceFactory {
        pagingSource(
            refreshKey = refreshKey,
            load = load,
            loadContext = loadContext
        )
    }

/**
 * Helper factory function to create [InvalidatingPagingSourceFactory]
 *
 * @param Key
 * @param Value
 * @param pagingSourceFactory
 * @receiver
 * @return
 */
fun <Key : Any, Value : Any> invalidatingPagingSourceFactory(
    pagingSourceFactory: PagingSourceFactory<Key, Value>
): InvalidatingPagingSourceFactory<Key, Value> =
    InvalidatingPagingSourceFactory(pagingSourceFactory = pagingSourceFactory)


