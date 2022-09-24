package com.treefrogapps.compose.responsiveui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.parallax.pager.HorizontalParallaxPager
import com.treefrogapps.compose.parallax.pager.rememberParallaxPagerState
import com.treefrogapps.compose.responsiveui.ui.theme.AppThemedContent
import com.treefrogapps.compose.responsiveui.ui.theme.WindowSize
import com.treefrogapps.compose.responsiveui.ui.theme.rememberWindowSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent(windowSize = rememberWindowSize())
        }
    }

    @Composable
    private fun MainActivityContent(windowSize: WindowSize) {
        AppThemedContent(windowSize = windowSize) {
            val state = rememberParallaxPagerState()
            HorizontalParallaxPager(
                state = state,
                pageCount = 6,
                backgroundContent = { idx ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color.Black)
                    )
                },
                foregroundContent = { idx ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(color = Color.Blue)
                    )
                })
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

