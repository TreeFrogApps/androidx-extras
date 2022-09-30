package com.treefrogapps.compose.gestures.swipeable

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmationSwipeable(
    state : SwipeableState<Int> = rememberSwipeableState(initialValue = 0),
    onConfirmed: () -> Unit = {},
    enabled : Boolean = true,
    isConfirmed : Boolean = false
) {

}