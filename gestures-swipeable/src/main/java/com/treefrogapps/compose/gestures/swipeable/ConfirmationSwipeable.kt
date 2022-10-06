package com.treefrogapps.compose.gestures.swipeable

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmationSwipeable(
    swipeableState: SwipeableState<ConfirmationState> = rememberSwipeableState(initialValue = ConfirmationState.Swipeable),
    toggleColor: Color = MaterialTheme.colors.primary,
    onConfirmed: suspend () -> Unit = {}
) {
    val density: Density = LocalDensity.current
    val minWidth: Dp = 200.dp
    val height: Dp = 42.dp
    var width: Dp by remember { mutableStateOf(minWidth) }
    val sliderWidth: Dp = 62.dp
    val shape = RoundedCornerShape(percent = 50)

    Box(
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
            .onGloballyPositioned { coords ->
                width = with(density) { max(coords.size.width.toDp(), minWidth) }
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
    shape: RoundedCornerShape
) {

    val swipeableBackgroundColor = Color.LightGray.copy(alpha = 0.2F)
    val swipedBackgroundColor = toggleColor.copy(alpha = 0.1F)
    val backgroundTintColor = remember(key1 = swipeableState) {
        Animatable(if (swipeableState.currentValue === ConfirmationState.Swiped) swipedBackgroundColor else swipeableBackgroundColor)
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
    width: Dp,
    height: Dp,
    sliderWidth: Dp,
    shape: RoundedCornerShape
) {
    val enabled by remember { derivedStateOf { swipeableState.currentValue === ConfirmationState.Swipeable } }
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
                    enabled = enabled,
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
    shape: RoundedCornerShape
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
                            color = Color.White.copy(alpha = 0.8F)
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
    block: suspend () -> Unit
) {
    LaunchedEffect(key1 = swipeableState) {
        snapshotFlow { swipeableState.currentValue }
            .drop(count = 1)
            .filter(ConfirmationState.Swiped::equals)
            .onEach { block() }
            .launchIn(scope = this)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationSwipeablePreview() {
    ConfirmationSwipeable()
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun ConfirmationSwipeableSwipedPreview() {
    ConfirmationSwipeable(
        swipeableState = rememberSwipeableState(initialValue = ConfirmationState.Swiped)
    )
}