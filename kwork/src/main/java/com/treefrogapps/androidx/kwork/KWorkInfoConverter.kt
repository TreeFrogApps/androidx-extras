package com.treefrogapps.androidx.kwork

import androidx.work.Data
import androidx.work.WorkInfo

object KWorkInfoConverter : (WorkInfo) -> KWorkInfo {

    override fun invoke(p1: WorkInfo): KWorkInfo =
        with(p1) {
            KWorkInfo(
                workId = id.toString(),
                state = state.toKWorkState(),
                progress = progress.extractProgress(),
                extraData = outputData.extractStringAllKeyValues()
            )
        }

    private fun WorkInfo.State.toKWorkState(): KWorkInfo.KWorkState =
        when (this) {
            WorkInfo.State.ENQUEUED,
            WorkInfo.State.BLOCKED -> KWorkInfo.KWorkState.Enqueued

            WorkInfo.State.CANCELLED -> KWorkInfo.KWorkState.Cancelled
            WorkInfo.State.FAILED -> KWorkInfo.KWorkState.Failed
            WorkInfo.State.SUCCEEDED -> KWorkInfo.KWorkState.Complete
            WorkInfo.State.RUNNING -> KWorkInfo.KWorkState.Running
        }

    private fun Data.extractProgress(): Int =
        getInt(KWorkManager.KWorkProgress, 0)

    private fun Data.extractStringAllKeyValues(): Map<String, String> =
        keyValueMap.mapValues { it.value as String }
}