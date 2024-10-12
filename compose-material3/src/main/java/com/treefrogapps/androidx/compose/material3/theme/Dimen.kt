package com.treefrogapps.androidx.compose.material3.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Companion.isMedium
import com.treefrogapps.androidx.compose.material3.theme.WindowSize.Companion.isSmall

/**
 * Reusable Dimensions
 */
@Immutable
class Dimens internal constructor(
    val zero: Dp = 0.dp,
    val hairline: Dp = 0.5.dp,
    val one: Dp = 1.dp,
    val two: Dp = 2.dp,
    val three: Dp = 3.dp,
    val four: Dp = 4.dp,
    val elevation: Elevation = Elevation(),
    val spacing: Spacing = Spacing(),
    val icon: Icon = Icon()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Dimens) return false

        if (zero != other.zero) return false
        if (hairline != other.hairline) return false
        if (one != other.one) return false
        if (two != other.two) return false
        if (three != other.three) return false
        if (four != other.four) return false
        if (elevation != other.elevation) return false
        if (spacing != other.spacing) return false
        if (icon != other.icon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = zero.hashCode()
        result = 31 * result + hairline.hashCode()
        result = 31 * result + one.hashCode()
        result = 31 * result + two.hashCode()
        result = 31 * result + three.hashCode()
        result = 31 * result + four.hashCode()
        result = 31 * result + elevation.hashCode()
        result = 31 * result + spacing.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }
}

@Immutable
class Elevation internal constructor(
    val normal: Dp = 2.dp,
    val high: Dp = 4.dp,
    val highest: Dp = 8.dp
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Elevation) return false

        if (normal != other.normal) return false
        if (high != other.high) return false
        if (highest != other.highest) return false

        return true
    }

    override fun hashCode(): Int {
        var result = normal.hashCode()
        result = 31 * result + high.hashCode()
        result = 31 * result + highest.hashCode()
        return result
    }
}

@Immutable
class Spacing internal constructor(
    val tiny: Dp = 4.dp,
    val small: Dp = 8.dp,
    val normal: Dp = 16.dp,
    val large: Dp = 24.dp,
    val big: Dp = 32.dp,
    val massive: Dp = 48.dp,
    val gigantic: Dp = 64.dp,
    val enormous: Dp = 128.dp
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Spacing) return false

        if (tiny != other.tiny) return false
        if (small != other.small) return false
        if (normal != other.normal) return false
        if (large != other.large) return false
        if (big != other.big) return false
        if (massive != other.massive) return false
        if (gigantic != other.gigantic) return false
        if (enormous != other.enormous) return false

        return true
    }

    override fun hashCode(): Int {
        var result = tiny.hashCode()
        result = 31 * result + small.hashCode()
        result = 31 * result + normal.hashCode()
        result = 31 * result + large.hashCode()
        result = 31 * result + big.hashCode()
        result = 31 * result + massive.hashCode()
        result = 31 * result + gigantic.hashCode()
        result = 31 * result + enormous.hashCode()
        return result
    }
}

@Immutable
class Icon internal constructor(
    val minuscule: Dp = 8.dp,
    val small: Dp = 16.dp,
    val notification: Dp = 24.dp,
    val normal: Dp = 32.dp,
    val large: Dp = 40.dp,
    val big: Dp = 48.dp,
    val massive: Dp = 64.dp,
    val gigantic: Dp = 96.dp,
    val enormous: Dp = 128.dp,
    val thumbnail: Dp = 164.dp
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Icon) return false

        if (minuscule != other.minuscule) return false
        if (small != other.small) return false
        if (notification != other.notification) return false
        if (normal != other.normal) return false
        if (large != other.large) return false
        if (big != other.big) return false
        if (massive != other.massive) return false
        if (gigantic != other.gigantic) return false
        if (enormous != other.enormous) return false
        if (thumbnail != other.thumbnail) return false

        return true
    }

    override fun hashCode(): Int {
        var result = minuscule.hashCode()
        result = 31 * result + small.hashCode()
        result = 31 * result + notification.hashCode()
        result = 31 * result + normal.hashCode()
        result = 31 * result + large.hashCode()
        result = 31 * result + big.hashCode()
        result = 31 * result + massive.hashCode()
        result = 31 * result + gigantic.hashCode()
        result = 31 * result + enormous.hashCode()
        result = 31 * result + thumbnail.hashCode()
        return result
    }
}

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
        enormous = 148.dp
    )
)
private val largeDimens: Dimens = Dimens(
    spacing = Spacing(
        tiny = 8.dp,
        small = 12.dp,
        normal = 24.dp,
        large = 32.dp,
        big = 40.dp,
        massive = 60.dp,
        gigantic = 80.dp,
        enormous = 160.dp
    )
)

internal val LocalAppDimens = staticCompositionLocalOf { smallDimens }

internal fun WindowSize.toDimens(): Dimens =
    when {
        isSmall() -> smallDimens
        isMedium() -> mediumDimens
        else -> largeDimens
    }