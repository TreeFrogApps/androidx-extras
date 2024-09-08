package com.treefrogapps.androidx.kwork

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import com.treefrogapps.androidx.kwork.KWorkRequest.Companion.isValidOrThrow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CancellationException
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class KWorkManager(
    private val workManager: WorkManager
) : KWork {

    internal object KWorkDirectExecutor : Executor {
        override fun execute(command: Runnable) = command.run()
        override fun toString(): String = "DirectExecutor"
    }

    companion object {
        internal const val KWorkTag = "kwork_tag"
        internal const val KWorkProgress = "kwork_progress_key"

        private fun <T> LiveData<T>.asFlow(): Flow<T> = channelFlow {
            val observer: Observer<T> = Observer<T>(this::trySend)
            observeForever(observer)
            awaitClose { removeObserver(observer) }
        }
    }

    override fun addWork(request: KWorkRequest) {
        request.isValidOrThrow()
        when (val workRequest = KWorkRequestConverter(request)) {
            is OneTimeWorkRequest -> workManager.enqueueUniqueWork(request.workId, ExistingWorkPolicy.REPLACE, workRequest)
            is PeriodicWorkRequest -> workManager.enqueueUniquePeriodicWork(request.workId, ExistingPeriodicWorkPolicy.UPDATE, workRequest)
        }
    }

    override fun cancelWorkById(workId: String) {
        workManager.cancelUniqueWork(workId)
    }

    override fun cancelWorkByTag(workTag: String) {
        workManager.cancelAllWorkByTag(workTag)
    }

    override fun cancelAllWork() {
        workManager.cancelAllWork()
    }

    override fun clean() {
        workManager.pruneWork()
    }

    override suspend fun workInfo(workId: String): KWorkInfo =
        workManager.getWorkInfosForUniqueWork(workId)
            .await()
            .firstOrNull()
            ?.let(KWorkInfoConverter::invoke)
            ?: KWorkInfo(workId = workId, state = KWorkInfo.KWorkState.Unknown)

    override fun observeWork(workId: String): Flow<KWorkInfo> =
        workManager.getWorkInfosForUniqueWorkLiveData(workId)
            .asFlow()
            .flowOn(context = Dispatchers.Main)
            .filter(List<WorkInfo>::isNotEmpty)
            .map(List<WorkInfo>::first)
            .map(KWorkInfoConverter::invoke)

    override fun observeWorkByTag(workTag: String): Flow<List<KWorkInfo>> =
        workManager.getWorkInfosByTagLiveData(workTag)
            .asFlow()
            .flowOn(context = Dispatchers.Main)
            .map { infoList -> infoList.mapNotNull(KWorkInfoConverter::invoke) }

    override fun observeAllWork(): Flow<List<KWorkInfo>> =
        workManager.getWorkInfosByTagLiveData(KWorkTag)
            .asFlow()
            .flowOn(context = Dispatchers.Main)
            .map { infoList -> infoList.mapNotNull(KWorkInfoConverter::invoke) }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun <V> ListenableFuture<V>.await(): V =
        suspendCancellableCoroutine { continuation ->
            addListener({
                try {
                    continuation.resume(get())
                } catch (throwable: Throwable) {
                    val cause = throwable.cause ?: throwable
                    when (throwable) {
                        is CancellationException -> continuation.cancel(cause)
                        else -> continuation.resumeWithException(cause)
                    }
                }
            }, KWorkDirectExecutor)
        }
}