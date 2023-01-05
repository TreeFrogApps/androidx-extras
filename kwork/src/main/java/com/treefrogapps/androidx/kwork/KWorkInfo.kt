package com.treefrogapps.androidx.kwork

data class KWorkInfo(
    val workId: String,
    val state: KWorkState = KWorkState.Enqueued,
    val progress: Int = 0,
    val retryOnFailure: Boolean = true,
    val extraData: Map<String, String> = emptyMap()
) {

    enum class KWorkState {
        Enqueued,
        Running,
        Failed,
        Complete,
        Cancelled,
        Unknown;

        fun isActive(): Boolean = this === Enqueued || this === Running

        fun isRunning(): Boolean = this === Running

        fun isEnqueued(): Boolean = this === Enqueued

        fun isNotActive(): Boolean = !isActive()

        fun hasFailed(): Boolean = this === Failed
    }
}