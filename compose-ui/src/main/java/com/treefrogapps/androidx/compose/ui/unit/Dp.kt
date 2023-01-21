package com.treefrogapps.androidx.compose.ui.unit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

fun Dp.toPaddingValues(): PaddingValues = PaddingValues(all = this)

fun Dp.toHorizontalPaddingValues(): PaddingValues = PaddingValues(horizontal = this)

fun Dp.toVerticalPaddingValues(): PaddingValues = PaddingValues(vertical = this)

@Composable
fun Dp.dpToPx() : Float = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Dp.dpToRoundedPx() : Int = with(LocalDensity.current) { this@dpToRoundedPx.roundToPx() }

@Composable
fun Int.pxToDp() : Dp = with(LocalDensity.current) { this@pxToDp.toDp() }
