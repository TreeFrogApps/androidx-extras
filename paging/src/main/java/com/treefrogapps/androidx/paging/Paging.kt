package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource

data class IndexedKey(
    val offset : Int,
    val requestedLoadSize : Int)

private fun  PagingSource.LoadParams<IndexedKey>.defaultNextAppendKey(loadedSize : Int) : () -> IndexedKey? = {
    if(loadedSize > 0) {
        IndexedKey(
            offset = loadedSize + (key?.offset ?: 0),
            requestedLoadSize = loadSize)
    } else null
}

private fun  PagingSource.LoadParams<IndexedKey>.defaultNextPrependKey(loadedSize : Int) : () -> IndexedKey? = {
    if(loadedSize > 0) {
        val offset = loadedSize - (key?.offset ?: 0).coerceAtLeast(minimumValue = 0)
        val requestedLoadSize = if(offset == 0) key?.offset ?: loadSize else loadedSize
        IndexedKey(
            offset = offset,
            requestedLoadSize = requestedLoadSize)
    } else null
}

/**
 * Helper function for index Int key based [PagingSource.LoadParams] to convert the request params
 * into a [PagingSource.LoadResult]
 */
fun <V : Any> PagingSource.LoadParams<IndexedKey>.toLoadResult(
    loaded : List<V>,
    nextKeyAppend : () -> IndexedKey? = defaultNextAppendKey(loadedSize = loaded.size),
    nextKeyPrepend : () -> IndexedKey? = defaultNextPrependKey(loadedSize = loaded.size),
) : PagingSource.LoadResult<IndexedKey, V> =
    when (this) {
        is PagingSource.LoadParams.Refresh -> refresh(currentKey = key, loaded = loaded)
        is PagingSource.LoadParams.Append  -> append(loaded = loaded, nextKey = nextKeyAppend)
        is PagingSource.LoadParams.Prepend -> prepend(loaded = loaded, nextKey = nextKeyPrepend)
    }

private fun <V : Any> refresh(
    currentKey: IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = currentKey)

private fun <V : Any> append(
    nextKey : () -> IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = nextKey())

private fun <V : Any> prepend(
    nextKey : () -> IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = nextKey())