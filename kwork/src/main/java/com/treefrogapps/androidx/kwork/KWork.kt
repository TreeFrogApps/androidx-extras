package com.treefrogapps.androidx.kwork

import kotlinx.coroutines.flow.Flow

/**
 * Kwork contract for queuing and managing work tasks
 */
interface KWork {

    /**
     * Add a work request
     */
    fun addWork(request: KWorkRequest)

    /**
     * Cancel a task by its [workId]
     */
    fun cancelWorkById(workId: String)

    /**
     * Cancel a task by its [workTag]
     */
    fun cancelWorkByTag(workTag: String)

    /**
     * Cancel all work - use with caution this will remove all pending work
     */
    fun cancelAllWork()

    /**
     * Remove completed, cancelled or failed tasks
     */
    fun clean()

    /**
     * Provide [KWorkInfo] for a given [workId]
     */
    suspend fun workInfo(workId: String): KWorkInfo?

    /**
     * Observe [KWorkInfo] changes to a given [workId]
     */
    fun observeWork(workId: String): Flow<KWorkInfo>

    /**
     * Observe [KWorkInfo] change by given [workTag]
     */
    fun observeWorkByTag(workTag: String): Flow<List<KWorkInfo>>

    /**
     * Observe all [KWorkInfo] changes
     */
    fun observeAllWork(): Flow<List<KWorkInfo>>
}