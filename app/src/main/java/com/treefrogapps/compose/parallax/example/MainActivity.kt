package com.treefrogapps.compose.parallax.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.parallax.pager.HorizontalParallaxPageIndicator
import com.treefrogapps.compose.parallax.pager.HorizontalParallaxPager
import com.treefrogapps.compose.parallax.pager.ParallaxPage
import com.treefrogapps.compose.parallax.pager.rememberParallaxPagerState
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
                        style = MaterialTheme.typography.h4
                    )
                },
                footer = {
                    Text(
                        modifier = Modifier.padding(all = 12.dp),
                        text = "Page Offset ${currentPagePixelOffset().value}",
                        style = MaterialTheme.typography.h6
                    )
                },
                content = {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(color = Color.Black)
                    )
                }).addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(color = Color.Blue)
                )
            }.addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Red)
                )
            }.addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Color.Green)
                )
            }
            (0 until 10).map { page }
        }

        MaterialTheme {
            Column {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val state = rememberParallaxPagerState()
                    var color by remember(state) { mutableStateOf(Color.Yellow.copy(alpha = 0.5F)) }
                    HorizontalParallaxPager(
                        modifier = Modifier.background(color = color),
                        state = state,
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
                                0    -> Color.Yellow
                                1    -> Color.Red
                                2    -> Color.Cyan
                                3    -> Color.Magenta
                                4    -> Color.Blue
                                else -> Color.Yellow
                            }.copy(alpha = max(a = 0.0F, b = 0.25F - abs(rtOffset) / 2))
                        }.launchIn(scope = this)
                    }
                }
            }
        }
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

