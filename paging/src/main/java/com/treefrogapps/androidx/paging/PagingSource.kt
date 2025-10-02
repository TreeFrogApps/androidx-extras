package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Factory function for creating a [PagingSource]
 *
 * [load] callback is NOT inlined, otherwise error catching logic would be escaped if it was as the compiler optimises the callback and
 * moves the code chunk to parent, escaping the try/catch
 */
inline fun <Key : Any, Value : Any> pagingSource(
    crossinline refreshKey: (state: PagingState<Key, Value>) -> Key?,
    noinline load: suspend (params: PagingSource.LoadParams<Key>) -> PagingSource.LoadResult<Key, Value>,
    loadContext: CoroutineContext = Dispatchers.IO
): PagingSource<Key, Value> = object : PagingSource<Key, Value>() {

    override fun getRefreshKey(state: PagingState<Key, Value>): Key? = refreshKey(state)

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value> = withContext(context = loadContext) {
        try {
            load(params)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            LoadResult.Error(throwable = e)
        }
    }
}


