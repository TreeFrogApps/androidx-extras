package com.treefrogapps.androidx.compose.material3

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@ExperimentalMaterial3Api
@Composable
fun rememberModalBottomSheetStateExtended(
    skipPartiallyExpanded: Boolean = false,
    confirmValueChange: (SheetValue) -> Boolean = { true }
): SheetStateExtended {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded,
        confirmValueChange = confirmValueChange
    )
    return remember { SheetStateExtended(sheetState = sheetState) }
}


@ExperimentalMaterial3Api
@Stable
class SheetStateExtended(val sheetState: SheetState) {

    var isShowing: Boolean by mutableStateOf(sheetState.isVisible)
        private set

    suspend fun show() {
        isShowing = true
        sheetState.show()
    }

    suspend fun hide() {
        sheetState.hide()
        isShowing = false
    }

    fun dismissed() {
        isShowing = false
    }
}