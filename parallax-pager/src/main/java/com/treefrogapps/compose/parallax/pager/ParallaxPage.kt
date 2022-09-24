package com.treefrogapps.compose.parallax.pager

import androidx.compose.runtime.Composable

class ParallaxPage(internal val background : @Composable ParallaxPagerScope.(page : Int) -> Unit) {

    internal val layers: MutableList<@Composable ParallaxPagerScope.(page : Int, layer : Int) -> Unit> = mutableListOf()
    fun addParallaxLayer(content: @Composable ParallaxPagerScope.(page : Int, layer : Int) -> Unit) : ParallaxPage {
        layers.add(content)
        return this
    }
}