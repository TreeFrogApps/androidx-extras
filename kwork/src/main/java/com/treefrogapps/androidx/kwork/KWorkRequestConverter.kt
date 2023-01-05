package com.treefrogapps.androidx.kwork

import androidx.work.*
import androidx.work.WorkRequest.Builder
import java.time.Duration

object KWorkRequestConverter : (KWorkRequest) -> WorkRequest {

    override fun invoke(p1: KWorkRequest): WorkRequest =
        p1.toBuilder().build()

    private fun KWorkRequest.toBuilder(): Builder<*, *> =
        when {
            isOneshot -> OneTimeWorkRequest.Builder(worker.java)
            else      -> PeriodicWorkRequest.Builder(worker.java, Duration.ofMillis(periodicIntervalMillis))
        }.setInitialDelay(Duration.ofMillis(initialDelay))
            .addTag(KWorkManager.KWorkTag)
            .addRequestTag(request = this)
            .setExpeditedIfPossible(request = this)
            .setRequestConstraints(request = this)
            .setRequestData(request = this)

    private fun Builder<*, *>.addRequestTag(request: KWorkRequest): Builder<*, *> =
        request.workTag?.let { tag -> addTag(tag) } ?: this

    private fun Builder<*, *>.setRequestConstraints(request: KWorkRequest): Builder<*, *> =
        setConstraints(
            Constraints.Builder()
                .setRequiresStorageNotLow(request.requiresStorageNotLow)
                .setRequiredNetworkType(if (request.requiresNetwork) NetworkType.CONNECTED else NetworkType.NOT_REQUIRED)
                .build())

    private fun Builder<*, *>.setRequestData(request: KWorkRequest): Builder<*, *> =
        setInputData(Data.Builder()
            .putAll(request.inputData)
            .build())

    private fun Builder<*, *>.setExpeditedIfPossible(request: KWorkRequest): Builder<*, *> =
        if (request.isOneshot) {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        } else this
}