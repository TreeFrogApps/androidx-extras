@file:Suppress("DEPRECATION")

package com.treefrogapps.androidx.compose.material.swipeable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.treefrogapps.androidx.compose.material.R
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.roundToInt

enum class ConfirmationState {
    Swipeable, Swiped
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmationSwipeable(
    swipeableState: SwipeableState<ConfirmationState> = rememberSwipeableState(initialValue = ConfirmationState.Swipeable),
    enabled: Boolean = true,
    toggleColor: Color = if (enabled) MaterialTheme.colors.secondary else Color.LightGray,
    onConfirmed: () -> Unit = {},
) {
    val density: Density = LocalDensity.current
    val minWidth = 200.dp
    val height = 42.dp
    val sliderWidth = 62.dp
    val shape = RoundedCornerShape(percent = 50)
    var width by remember { mutableStateOf(minWidth) }

    Box(
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
            .onGloballyPositioned { layout ->
                with(density) {
                    width = max(layout.size.width.toDp(), minWidth)
                }
            }
    ) {
        SliderBackground(
            swipeableState = swipeableState,
            toggleColor = toggleColor,
            width = width,
            height = height,
            shape = shape
        )
        SliderTarget(
            swipeableState = swipeableState,
            toggleColor = toggleColor,
            enabled = enabled,
            width = width,
            height = height,
            sliderWidth = sliderWidth,
            shape = shape
        )
    }

    SwipeableConfirmedLaunchedEffect(
        swipeableState = swipeableState,
        block = onConfirmed
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SliderBackground(
    swipeableState: SwipeableState<ConfirmationState>,
    toggleColor: Color,
    width: Dp,
    height: Dp,
    shape: RoundedCornerShape,
) {

    val swipeableBackgroundColor = Color.LightGray.copy(alpha = 0.2F)
    val swipedBackgroundColor = toggleColor.copy(alpha = 0.15F)
    val backgroundTintColor = remember(key1 = swipeableState.currentValue) {
        Animatable(
            if (swipeableState.currentValue === ConfirmationState.Swiped) {
                swipedBackgroundColor
            } else swipeableBackgroundColor
        )
    }
    Box(
        modifier = Modifier
            .size(width, height)
            .padding(all = 8.dp)
            .background(
                color = backgroundTintColor.value,
                shape = shape
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedVisibility(
            visible = swipeableState.currentValue === ConfirmationState.Swiped,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Icon(
                modifier = Modifier.padding(start = 2.dp),
                painter = painterResource(id = R.drawable.ic_confirmation_badge),
                contentDescription = null,
                tint = toggleColor
            )
        }
    }

    SwipeableConfirmedLaunchedEffect(swipeableState = swipeableState) {
        backgroundTintColor.animateTo(
            targetValue = swipedBackgroundColor,
            animationSpec = tween(durationMillis = 250)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SliderTarget(
    swipeableState: SwipeableState<ConfirmationState>,
    toggleColor: Color,
    enabled: Boolean,
    width: Dp,
    height: Dp,
    sliderWidth: Dp,
    shape: RoundedCornerShape,
) {
    val sliderEnabled by remember(enabled) { derivedStateOf { swipeableState.currentValue === ConfirmationState.Swipeable && enabled } }
    val startState = 0f to ConfirmationState.Swipeable
    val endState = with(LocalDensity.current) { width.toPx() - sliderWidth.toPx() } to ConfirmationState.Swiped
    val anchorStates: Map<Float, ConfirmationState> = mapOf(startState, endState)

    Box(
        modifier = Modifier.size(width, height),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .swipeable(
                    state = swipeableState,
                    enabled = sliderEnabled,
                    anchors = anchorStates,
                    thresholds = { _, _ -> FixedThreshold(offset = endState.first.dp) },
                    orientation = Orientation.Horizontal,
                    velocityThreshold = Dp.Infinity
                )
        ) {
            Card(
                modifier = Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .width(width = sliderWidth)
                    .height(height = height)
                    .padding(all = 2.dp),
                backgroundColor = toggleColor,
                elevation = 4.dp,
                shape = shape
            ) {
                SliderTargetContent(
                    height = height,
                    shape = shape
                )
            }
        }
    }
}

@Composable
private fun SliderTargetContent(
    height: Dp,
    shape: RoundedCornerShape,
) {
    Box(contentAlignment = Alignment.Center) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            (0 until 3).forEach { _ ->
                Box(
                    modifier = Modifier
                        .background(
                            shape = shape,
                            color = MaterialTheme.colors.surface.copy(alpha = 0.9F)
                        )
                        .width(width = 3.dp)
                        .height(height = height / 3)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeableConfirmedLaunchedEffect(
    swipeableState: SwipeableState<ConfirmationState>,
    block: suspend () -> Unit,
) {
    LaunchedEffect(key1 = swipeableState, key2 = block) {
        snapshotFlow { swipeableState.currentValue }
            .filter(ConfirmationState.Swiped::equals)
            .onEach { block() }
            .launchIn(scope = this)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationSwipeablePreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationSwipeableSwipedPreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable(
                swipeableState = rememberSwipeableState(initialValue = ConfirmationState.Swiped)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationDisabledSwipeablePreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable(enabled = false)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ConfirmationSwipeableDarkPreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ConfirmationSwipeableSwipedDarkPreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable(
                swipeableState = rememberSwipeableState(initialValue = ConfirmationState.Swiped)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ConfirmationSwipeableDisabledDarkPreview() {
    MaterialTheme {
        Surface {
            ConfirmationSwipeable(enabled = false)
        }
    }
}