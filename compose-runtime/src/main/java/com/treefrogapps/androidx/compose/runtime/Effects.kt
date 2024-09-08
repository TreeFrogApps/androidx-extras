package com.treefrogapps.androidx.compose.runtime

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner


@Composable
fun LifecycleDisposableEffect(
    key1: Any? = null,
    key2: Any? = null,
    key3: Any? = null,
    onCreate: () -> Unit = {},
    onStart: () -> Unit = {},
    onResume: () -> Unit = {},
    onPause: () -> Unit = {},
    onStop: () -> Unit = {},
    onDestroy: () -> Unit = {},
    onDispose: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = key1, key2 = key2, key3 = key3) {
        val observer = object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) = onCreate()
            override fun onStart(owner: LifecycleOwner) = onStart()
            override fun onResume(owner: LifecycleOwner) = onResume()
            override fun onPause(owner: LifecycleOwner) = onPause()
            override fun onStop(owner: LifecycleOwner) = onStop()
            override fun onDestroy(owner: LifecycleOwner) = onDestroy()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            onDispose()
        }
    }
}