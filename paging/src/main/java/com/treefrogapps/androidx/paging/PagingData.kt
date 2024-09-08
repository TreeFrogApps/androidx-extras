package com.treefrogapps.androidx.paging

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

fun <T : Any> errorPagingDataOf(
    e: Throwable,
    sourceLoadStates: LoadStates = LoadStates(
        refresh = LoadState.Error(e),
        prepend = LoadState.NotLoading(endOfPaginationReached = true),
        append = LoadState.NotLoading(endOfPaginationReached = true)
    ),
    mediatorLoadStates: LoadStates? = null
): PagingData<T> =
    PagingData.empty(
        sourceLoadStates = sourceLoadStates,
        mediatorLoadStates = mediatorLoadStates
    )