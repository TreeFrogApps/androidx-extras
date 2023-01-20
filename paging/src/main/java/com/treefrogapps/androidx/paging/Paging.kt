package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource

data class IndexedKey(
    val page : Int,
    val offset: Int)

private fun PagingSource.LoadParams<IndexedKey>.defaultNextAppendKey(loadedSize: Int): () -> IndexedKey? = {
    if (loadedSize == loadSize) {
        IndexedKey(
            page = key?.page?.let { p -> p + 1 } ?: 0,
            offset = loadedSize + (key?.offset ?: 0))
    } else null
}

private fun PagingSource.LoadParams<IndexedKey>.defaultNextPrependKey(loadedSize: Int): () -> IndexedKey? = {
    if (loadedSize == loadSize) {
        IndexedKey(
            page = key?.page?.let { p -> p - 1 } ?: 0,
            offset = (key?.offset ?: 0) - loadedSize)
    } else null
}

/**
 * Helper function for index Int key based [PagingSource.LoadParams] to convert the request params
 * into a [PagingSource.LoadResult]
 */
fun <V : Any> PagingSource.LoadParams<IndexedKey>.toLoadResult(
    loaded: List<V>,
    nextKeyAppend: () -> IndexedKey? = defaultNextAppendKey(loadedSize = loaded.size),
    nextKeyPrepend: () -> IndexedKey? = defaultNextPrependKey(loadedSize = loaded.size),
): PagingSource.LoadResult<IndexedKey, V> =
    when (this) {
        is PagingSource.LoadParams.Refresh -> refresh(nextKey = nextKeyAppend, loaded = loaded)
        is PagingSource.LoadParams.Append  -> append(nextKey = nextKeyAppend, loaded = loaded)
        is PagingSource.LoadParams.Prepend -> prepend(nextKey = nextKeyPrepend, loaded = loaded)
    }

private fun <V : Any> refresh(
    nextKey: () -> IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = nextKey())

private fun <V : Any> append(
    nextKey: () -> IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = nextKey())

private fun <V : Any> prepend(
    nextKey: () -> IndexedKey?,
    loaded: List<V>
): PagingSource.LoadResult<IndexedKey, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = nextKey())