package com.treefrogapps.androidx.compose.activity

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Wrapper function to resolve some devices, mainly using MIUI 13, where
 * upon rotation a white screen is presented to the user until the user touches the screen.
 *
 * This mainly happens when using Jetpack Compose Navigation library.
 *
 * [Issue tracker Link](https://issuetracker.google.com/issues/227926002) - marked as "Won't Fix"
 *
 * This is a platform bug, however needs to be accounted for in apps that intend to target
 * these devices.
 */
fun ComponentActivity.setContent(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit
) {
    setContent(parent = parent, content = content)

    lifecycleScope.launch {
        delay(timeMillis = 30)
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }
}