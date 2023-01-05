package com.treefrogapps.androidx.kwork

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.core.content.getSystemService
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext

/**
 * Class that represents a work container that is invoked by [androidx.work.WorkManager]
 */
abstract class KWorker(
    protected val notificationId: Int,
    protected val context: Context,
    protected val parameters: WorkerParameters,
) : CoroutineWorker(context, parameters) {

    companion object {
        private val Permits: Int = Runtime.getRuntime().availableProcessors() / 2
        private val cooperativeWorkSemaphore = Semaphore(permits = Permits)

        @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
        private fun isEqualOrAboveApi29(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    private val notificationManager: NotificationManager by lazy { context.getSystemService()!! }

    final override suspend fun getForegroundInfo(): ForegroundInfo {
        createChannel(manager = notificationManager)
        return if (isEqualOrAboveApi29()) {
            ForegroundInfo(
                notificationId,
                createNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            ForegroundInfo(
                notificationId,
                createNotification())
        }
    }

    final override suspend fun doWork(): Result = withContext(context = Dispatchers.IO) {
        cooperativeWorkSemaphore.withPermit { doWorkCooperatively() }
    }

    abstract suspend fun doWorkCooperatively(): Result

    abstract fun createChannel(manager: NotificationManager)

    abstract fun createNotification(): Notification

    protected suspend fun updateProgress(progress: Int) {
        setProgress(workDataOf(KWorkManager.KWorkProgress to progress))
    }

    protected fun <T> kotlin.Result<T>.toListenableResult(
        successData: Data = Data.EMPTY,
        failureData: Data = Data.EMPTY,
        retryOnFailure: (Throwable?) -> Boolean = { true },
    ): Result = when {
        isSuccess                         -> Result.success(successData)
        retryOnFailure(exceptionOrNull()) -> Result.retry()
        else                              -> Result.failure(failureData)
    }
}