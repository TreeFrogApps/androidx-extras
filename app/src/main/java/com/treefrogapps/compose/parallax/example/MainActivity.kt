package com.treefrogapps.compose.parallax.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.parallax.example.ui.theme.AppThemedContent
import com.treefrogapps.compose.parallax.example.ui.theme.WindowSize
import com.treefrogapps.compose.parallax.example.ui.theme.rememberWindowSize
import com.treefrogapps.compose.parallax.pager.HorizontalParallaxPager
import com.treefrogapps.compose.parallax.pager.ParallaxPage
import com.treefrogapps.compose.parallax.pager.rememberParallaxPagerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(windowSize = rememberWindowSize())
        }
    }

    @Composable
    private fun MainActivityContent(windowSize: WindowSize) {
        val pages = remember {
            val page = ParallaxPage { page ->
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(color = Color.Black)
                )
            }.addParallaxLayer { page, layer ->
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(color = Color.Blue)
                )
            }.addParallaxLayer { page, layer ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Red)
                )
            }.addParallaxLayer { page, layer ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Color.Green)
                )
            }
            listOf(page, page, page, page, page)
        }
        AppThemedContent(windowSize = windowSize) {
            val state = rememberParallaxPagerState()
            HorizontalParallaxPager(
                state = state,
                pages = pages
            )
        }
    }

    @Composable
    @Preview(
        showSystemUi = true,
        showBackground = true,
        device = Devices.PIXEL_4
    )
    private fun MainActivityPreview() {
        MainActivityContent(windowSize = WindowSize.SmallPortrait)
    }
}

