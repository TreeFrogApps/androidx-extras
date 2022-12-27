package com.treefrogapps.compose.material.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.material.theme.WindowSize.Companion.isMedium
import com.treefrogapps.compose.material.theme.WindowSize.Companion.isSmall

/**
 * Reusable Dimensions
 */
data class Dimens internal constructor(
    val zero: Dp = 0.dp,
    val hairline: Dp = 0.5.dp,
    val one: Dp = 1.dp,
    val elevation: Elevation = Elevation(),
    val spacing: Spacing = Spacing(),
    val icon: Icon = Icon())

data class Elevation internal constructor(
    val normal: Dp = 2.dp,
    val high: Dp = 4.dp,
    val highest: Dp = 8.dp)

data class Spacing internal constructor(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val normal: Dp = 16.dp,
    val large: Dp = 24.dp,
    val big: Dp = 32.dp,
    val massive: Dp = 48.dp,
    val gigantic: Dp = 64.dp,
    val enormous: Dp = 128.dp)

data class Icon internal constructor(
    val minuscule: Dp = 8.dp,
    val small: Dp = 16.dp,
    val notification: Dp = 24.dp,
    val normal: Dp = 32.dp,
    val large: Dp = 40.dp,
    val big: Dp = 48.dp,
    val massive: Dp = 64.dp,
    val gigantic: Dp = 96.dp,
    val enormous: Dp = 128.dp,
    val thumbnail: Dp = 164.dp)

private val smallDimens: Dimens = Dimens()
private val mediumDimens: Dimens = Dimens(
    spacing = Spacing(
        tiny = 6.dp,
        small = 10.dp,
        normal = 20.dp,
        large = 28.dp,
        big = 36.dp,
        massive = 54.dp,
        gigantic = 72.dp,
        enormous = 148.dp))
private val largeDimens: Dimens = Dimens(
    spacing = Spacing(
        tiny = 8.dp,
        small = 12.dp,
        normal = 24.dp,
        large = 32.dp,
        big = 40.dp,
        massive = 60.dp,
        gigantic = 80.dp,
        enormous = 160.dp))

internal val LocalAppDimens = staticCompositionLocalOf { smallDimens }

internal fun WindowSize.toDimens(): Dimens =
    when {
        isSmall()  -> smallDimens
        isMedium() -> mediumDimens
        else       -> largeDimens
    }