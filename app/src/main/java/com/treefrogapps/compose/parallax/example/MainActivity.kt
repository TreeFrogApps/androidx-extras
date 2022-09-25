package com.treefrogapps.compose.parallax.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.treefrogapps.compose.parallax.pager.HorizontalParallaxPager
import com.treefrogapps.compose.parallax.pager.ParallaxPage
import com.treefrogapps.compose.parallax.pager.rememberParallaxPagerState

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
            val page = ParallaxPage {
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(color = Color.Black)
                )
            }.addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .offset(y = 50.dp)
                        .background(color = Color.Blue)
                )
            }.addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .offset(y = 100.dp)
                        .background(color = Color.Red)
                )
            }.addParallaxLayer { _, _ ->
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .offset(y = 150.dp)
                        .background(color = Color.Green)
                )
            }.addParallaxLayer { page, _ ->
                Text(
                    modifier = Modifier.offset(y = 200.dp),
                    text = "Page $page",
                    style = MaterialTheme.typography.h5
                )
            }
            listOf(page, page, page, page, page)
        }

        MaterialTheme {
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
        MainActivityContent()
    }
}

