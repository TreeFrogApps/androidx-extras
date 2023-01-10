package com.treefrogapps.androidx.paging

/**
 * Represents a interface that has a single method that can be invoked to invalidate
 * a [androidx.paging.PagingSource].
 */
interface PagingSourceInvalidator {

    fun invalidateSource()
}