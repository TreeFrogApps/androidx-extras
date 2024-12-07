package com.treefrogapps.androidx.paging

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.PagingSource
import androidx.paging.PagingSourceFactory
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Index key the [PagingSource] key
 *
 * @property page the current page - zero based index
 * @property offset the current offset within a page - zero based index
 */
data class IndexKey(
    val page: Int = 0,
    val offset: Int = 0
)

/**
 * Index key paging source factory delegate convenience function wrapper to create a [InvalidatingPagingSourceFactory]
 *
 * @param Value the generic [PagingSource] value
 * @param loadList the suspending load function to provide a [List] from [PagingSource.LoadParams]
 * @param loadContext the [CoroutineContext] to used for [loadList] function
 *
 * @return a [InvalidatingPagingSourceFactory]
 */
inline fun <Value : Any> invalidatingIndexKeyPagingSourceFactory(
    crossinline loadList: suspend (params: PagingSource.LoadParams<IndexKey>) -> List<Value>,
    crossinline refreshKey: (state: PagingState<IndexKey, Value>) -> IndexKey? = { IndexKey() },
    loadContext: CoroutineContext = Dispatchers.IO
): InvalidatingPagingSourceFactory<IndexKey, Value> =
    invalidatingPagingSourceFactory(
        pagingSourceFactory = indexKeyPagingSourceFactory(
            refreshKey = refreshKey,
            loadList = loadList,
            loadContext = loadContext
        )
    )

/**
 * Index key paging source factory is a convenience wrapper around [pagingSource] function that implements a refresh key.
 *
 * @param Value the generic [PagingSource] value
 * @param loadList the suspending load function to provide a [List] from [PagingSource.LoadParams]
 * @param loadContext the [CoroutineContext] to used for [loadList] function
 *
 * @return a [PagingSource] object that should be used until it is invalidated, at which point a refresh [IndexKey] is provided to the
 * next generation paging source.
 */
inline fun <Value : Any> indexKeyPagingSourceFactory(
    crossinline loadList: suspend (params: PagingSource.LoadParams<IndexKey>) -> List<Value>,
    crossinline refreshKey: (state: PagingState<IndexKey, Value>) -> IndexKey? = { IndexKey() },
    loadContext: CoroutineContext = Dispatchers.IO
): PagingSourceFactory<IndexKey, Value> =
    pagingSourceFactory(
        refreshKey = refreshKey,
        load = { params -> params.toLoadResult(loaded = loadList(params)) },
        loadContext = loadContext
    )

/**
 * Create refresh index key a convenient function to create a [IndexKey] refresh key from a [PagingState]
 *
 * Note : Use with caution, this assumes only updates to pagination, not creates, or deletes.  In this situation
 * this function should not be used as the underlying collection you are paginating is not stable and indexes can change.
 *
 * @param Value the generic [PagingSource] value
 * @param pagingState the current [PagingState]
 * @return a [IndexKey] that represents where the new [PagingSource] should start pagination.
 */
fun <Value : Any> createRefreshIndexKey(pagingState: PagingState<IndexKey, Value>): IndexKey =
    pagingState.anchorPosition?.let { position ->
        if (position > 0) {
            val initialSize = pagingState.config.initialLoadSize
            if (position > initialSize) {
                val pageSize = pagingState.config.pageSize
                val page = position / pageSize
                val offset = pageSize * page
                return IndexKey(page = page, offset = offset)
            } else null
        } else null
    } ?: IndexKey()

/**
 * Helper function for index Int key based [PagingSource.LoadParams] to convert the request params
 * into a [PagingSource.LoadResult]
 *
 * @param Value the generic [PagingSource] value
 * @param loaded the collection of [Value] items loaded
 * @param prevKey provide the previous key [IndexKey] or null for [PagingSource.LoadParams]
 * @param nextKey provide the next key [IndexKey] or null for [PagingSource.LoadParams]
 *
 * @receiver in the scope of [PagingSource.LoadParams]
 *
 * @return the mapped [PagingSource.LoadResult.Page] with the relevant [IndexKey] for prev and next
 */
fun <Value : Any> PagingSource.LoadParams<IndexKey>.toLoadResult(
    loaded: List<Value>,
    prevKey: IndexKey? = defaultPrevKey(),
    nextKey: IndexKey? = defaultNextKey(loadedSize = loaded.size),
): PagingSource.LoadResult<IndexKey, Value> =
    PagingSource.LoadResult.Page(
        data = loaded,
        prevKey = prevKey,
        nextKey = nextKey
    )

private fun PagingSource.LoadParams<IndexKey>.defaultPrevKey(): IndexKey? =
    key?.run {
        if (offset > 0 && page > 0) {
            IndexKey(
                page = page - 1,
                offset = offset - loadSize
            )
        } else null
    }

private fun PagingSource.LoadParams<IndexKey>.defaultNextKey(loadedSize: Int): IndexKey? =
    if (loadedSize == loadSize) {
        IndexKey(
            page = key?.page?.let { p -> p + 1 } ?: 1,
            offset = loadedSize + (key?.offset ?: 0))
    } else null
