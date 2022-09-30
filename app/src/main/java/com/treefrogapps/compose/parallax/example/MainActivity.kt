package com.treefrogapps.compose.parallax.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.parallax.pager.*
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.math.abs
import kotlin.math.max

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }

    @Composable
    private fun MainActivityContent() {
        val pages = remember {
            val page = ParallaxPage(
                header = { pageIdx ->
                    Text(
                        modifier = Modifier.padding(all = 12.dp),
                        text = "Page $pageIdx",
                        style = MaterialTheme.typography.h5
                    )
                },
                footer = {
                    Text(
                        modifier = Modifier.padding(all = 12.dp),
                        text = "Page Offset ${currentPagePixelOffset().value}",
                        style = MaterialTheme.typography.body1
                    )
                },
                content = {
                    ParallaxContent(size = 25.dp, color = Color.Blue)
                })
                .addParallaxLayer { _, _ ->
                    ParallaxContent(size = 25.dp, color = Color.Blue.copy(alpha = 0.8F))
                }.addParallaxLayer { _, _ ->
                    ParallaxContent(size = 25.dp, color = Color.Blue.copy(alpha = 0.7F))
                }.addParallaxLayer { _, _ ->
                    ParallaxContent(size = 25.dp, color = Color.Blue.copy(alpha = 0.6F))
                }.addParallaxLayer { _, _ ->
                    ParallaxContent(size = 25.dp, color = Color.Blue.copy(alpha = 0.5F))
                }
            (0 until 10).map { page }
        }

        MaterialTheme {
            Column {
                Box(
                    modifier = Modifier.weight(weight = 0.5F),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val state = rememberParallaxPagerState()
                    var color by remember(state) { mutableStateOf(Color.Yellow.copy(alpha = 0.5F)) }
                    HorizontalParallaxPager(
                        modifier = Modifier.background(color = color),
                        state = state,
                        mode = ParallaxMode.Stacked,
                        effect = ParallaxEffect.Medium,
                        pages = pages
                    )
                    HorizontalParallaxPageIndicator(
                        modifier = Modifier.padding(vertical = 16.dp),
                        state = state,
                        pageCount = pages.size,
                        pageIndicatorColor = Color.LightGray,
                        activePageIndicatorColor = Color.Blue
                    )

                    LaunchedEffect(key1 = state) {
                        combine(
                            snapshotFlow { state.currentPage.value },
                            snapshotFlow { state.currentPagePixelOffset.value },
                            snapshotFlow { state.currentPageRatioOffset.value },
                            ::Triple
                        ).onEach { (page, _, rtOffset) ->
                            color = when (page) {
                                0 -> Color.Yellow
                                1 -> Color.Red
                                2 -> Color.Cyan
                                3 -> Color.Magenta
                                4 -> Color.Blue
                                else -> Color.Yellow
                            }.copy(alpha = max(a = 0.0F, b = 0.25F - abs(rtOffset) / 2))
                        }.launchIn(scope = this)
                    }
                }
                Box(
                    modifier = Modifier.weight(weight = 0.5F),
                    contentAlignment = Alignment.CenterStart
                ) {
                    val state = rememberParallaxPagerState()
                    var color by remember(state) { mutableStateOf(Color.Yellow.copy(alpha = 0.5F)) }
                    VerticalParallaxPager(
                        modifier = Modifier.background(color = color),
                        state = state,
                        mode = ParallaxMode.Stacked,
                        effect = ParallaxEffect.Medium,
                        pages = pages
                    )
                    VerticalParallaxPageIndicator(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        state = state,
                        pageCount = pages.size,
                        pageIndicatorColor = Color.LightGray,
                        activePageIndicatorColor = Color.Blue
                    )

                    LaunchedEffect(key1 = state) {
                        combine(
                            snapshotFlow { state.currentPage.value },
                            snapshotFlow { state.currentPagePixelOffset.value },
                            snapshotFlow { state.currentPageRatioOffset.value },
                            ::Triple
                        ).onEach { (page, _, rtOffset) ->
                            color = when (page) {
                                0 -> Color.Yellow
                                1 -> Color.Red
                                2 -> Color.Cyan
                                3 -> Color.Magenta
                                4 -> Color.Blue
                                else -> Color.Yellow
                            }.copy(alpha = max(a = 0.0F, b = 0.25F - abs(rtOffset) / 2))
                        }.launchIn(scope = this)
                    }
                }
            }
        }
    }

    @Composable
    private fun ParallaxContent(size: Dp, color: Color) {
        Box(
            modifier = Modifier
                .size(size = size)
                .background(color = color)
        )
    }

    @Composable
    @Preview(
        showSystemUi = true,
        showBackground = true,
        device = Devices.PIXEL_4
    )
    private fun MainActivityPreview() {
        MainActivityContent()
    }
}

