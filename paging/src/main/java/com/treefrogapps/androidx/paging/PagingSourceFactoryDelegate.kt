package com.treefrogapps.androidx.paging

import androidx.paging.PagingSource
import java.util.concurrent.atomic.AtomicReference

/**
 * A Class that represents a [PagingSourceInvalidator] and [PagingSource] factory.
 * The purpose of this class is to be both the [androidx.paging.Pager] source factory
 * and also responsible for invalidating the current [PagingSource].
 *
 * This is to bridge between the the paging library which does not expose the current
 * [PagingSource] so it is up to the client to be able to call [PagingSource.invalidate].
 * This will invoke the internal [androidx.paging.Pager] to generate a new [PagingSource]
 * using this factory, and reload data using [PagingSource.getRefreshKey] from the previous
 * [PagingSource] as a hint where to load data from.
 */
class PagingSourceFactoryDelegate<K : Any, V : Any>(
    private val factory: () -> PagingSource<K, V>
) : PagingSourceInvalidator, () -> PagingSource<K, V> {

    private var currentSource: AtomicReference<PagingSource<K, V>?> = AtomicReference(null)

    /**
     * Factory method to create a new [PagingSource]. Used in conjunction
     * with [invalidateSource].
     */
    override fun invoke(): PagingSource<K, V> {
        val source = factory()
        currentSource.set(source)
        return source
    }

    /**
     * Invalidate the current source factory, if exists.  Calling this method multiple times
     * should be idempotent.
     */
    override fun invalidateSource() {
        currentSource.get()?.let(PagingSource<K, V>::invalidate)
    }
}