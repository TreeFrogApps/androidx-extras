package com.treefrogapps.compose.parallax.pager

import androidx.compose.runtime.Composable

class ParallaxPage {

    internal val layers: MutableList<(@Composable PagerScope.(page : Int, layer : Int) -> Unit)> = mutableListOf()

    fun addLayer(content: @Composable PagerScope.(page : Int, layer : Int) -> Unit) {
        layers.add(content)
    }
}