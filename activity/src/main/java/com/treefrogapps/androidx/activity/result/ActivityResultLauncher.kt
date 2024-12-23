package com.treefrogapps.androidx.activity.result

import android.content.ActivityNotFoundException
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat

inline fun ActivityResultLauncher<Unit>.launch(
    options: ActivityOptionsCompat? = null,
    onError: () -> Unit = {}
) = try {
    launch(input = Unit, options = options)
} catch (e: ActivityNotFoundException) {
    onError()
}

inline fun <I : Any?> ActivityResultLauncher<I?>.launchNullable(
    input: I? = null,
    options: ActivityOptionsCompat? = null,
    onError: () -> Unit = {}
) = try {
    launch(input = input, options = options)
} catch (e: ActivityNotFoundException) {
    onError()
}

inline fun <I : Any> ActivityResultLauncher<I>.launchNotNull(
    input: I,
    options: ActivityOptionsCompat? = null,
    onError: () -> Unit = {}
) = try {
    launch(input = input, options = options)
} catch (e: ActivityNotFoundException) {
    onError()
}

