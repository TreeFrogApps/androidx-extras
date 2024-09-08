package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Factory function for creating a [PagingSource]
 */
fun <K : Any, V : Any> pagingSource(
    refreshKey: (state: PagingState<K, V>) -> K?,
    load: suspend (params: PagingSource.LoadParams<K>) -> PagingSource.LoadResult<K, V>,
    loadContext: CoroutineContext = Dispatchers.IO
): PagingSource<K, V> = object : PagingSource<K, V>() {

    override fun getRefreshKey(state: PagingState<K, V>): K? = refreshKey(state)

    override suspend fun load(params: LoadParams<K>): LoadResult<K, V> = withContext(context = loadContext) {
        load(params)
    }
}


