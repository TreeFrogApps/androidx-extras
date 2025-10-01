package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Factory function for creating a [PagingSource]
 */
inline fun <Key : Any, Value : Any> pagingSource(
    crossinline refreshKey: (state: PagingState<Key, Value>) -> Key?,
    crossinline load: suspend (params: PagingSource.LoadParams<Key>) -> PagingSource.LoadResult<Key, Value>,
    loadContext: CoroutineContext = Dispatchers.IO
): PagingSource<Key, Value> = object : PagingSource<Key, Value>() {

    override fun getRefreshKey(state: PagingState<Key, Value>): Key? = refreshKey(state)

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> = withContext(context = loadContext) {
        try {
            load(params)
        } catch (e : Exception) {
            if(e is CancellationException) throw e
            LoadResult.Error(throwable = e)
        }
    }
}


