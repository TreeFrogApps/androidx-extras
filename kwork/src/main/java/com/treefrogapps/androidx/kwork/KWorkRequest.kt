package com.treefrogapps.androidx.kwork

import androidx.work.Worker
import kotlin.reflect.KClass

/**
 * Work Request object that defines constraints, options and unique identifiers about a work request.
 *
 * Clients can provide additional [inputData] that will delivered to the underlying [KWorker]
 * allowing additional parameters and information for performing work.
 *
 * Valid input data types are [Boolean], [Int], [Long], [Float], [Double], [String], and their
 * array versions. If other data types are submitted these will throw a runtime error and is not
 * parsed at compile time.
 */
data class KWorkRequest(
    val workId: String,
    val workTag: String? = null,
    val worker: KClass<out Worker>,
    val requiresNetwork: Boolean = false,
    val requiresStorageNotLow: Boolean = true,
    val periodicIntervalMillis: Long = MIN_PERIODIC_INTERVAL_MILLIS,
    val initialDelay: Long = 0,
    val isOneshot: Boolean = true,
    val inputData: Map<String, Any> = emptyMap()
) {

    companion object {

        private const val MIN_PERIODIC_INTERVAL_MILLIS: Long = 1000 * 60 * 15

        internal fun KWorkRequest.isValidOrThrow() {
            if (!isOneshot && periodicIntervalMillis < MIN_PERIODIC_INTERVAL_MILLIS) {
                throw IllegalArgumentException("Periodic interval must be greater than $MIN_PERIODIC_INTERVAL_MILLIS")
            }

            if (initialDelay < 0) {
                throw IllegalArgumentException("Initial delay cannot be less than zero")
            }
        }
    }
}


