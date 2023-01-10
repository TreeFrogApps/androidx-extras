package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource

/**
 * Helper function for index Int key based [PagingSource.LoadParams] to convert the request params
 * into a [PagingSource.LoadResult]
 */
fun <V : Any> PagingSource.LoadParams<Int>.toLoadResult(loaded : List<V>) : PagingSource.LoadResult<Int, V> =
    when (this) {
        is PagingSource.LoadParams.Refresh -> refresh(currentKey = key, loaded = loaded)
        is PagingSource.LoadParams.Append  -> append(currentKey = key, loaded = loaded)
        is PagingSource.LoadParams.Prepend -> prepend(currentKey = key, loaded = loaded)
    }

private fun <V : Any> refresh(
    currentKey: Int?,
    loaded: List<V>
): PagingSource.LoadResult<Int, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = currentKey)

private fun <V : Any> append(
    currentKey: Int,
    loaded: List<V>
): PagingSource.LoadResult<Int, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = currentKey + loaded.size)

private fun <V : Any> prepend(
    currentKey: Int,
    loaded: List<V>
): PagingSource.LoadResult<Int, V> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = null,
        nextKey = currentKey - loaded.size)