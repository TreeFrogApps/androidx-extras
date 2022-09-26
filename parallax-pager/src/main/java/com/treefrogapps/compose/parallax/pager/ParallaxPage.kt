package com.treefrogapps.compose.parallax.pager

import androidx.compose.runtime.Composable

class ParallaxPage(
    internal val header : (@Composable ParallaxPagerScope.(page : Int) -> Unit)? = null,
    internal val footer : (@Composable ParallaxPagerScope.(page : Int) -> Unit)? = null,
    internal val content : @Composable ParallaxPagerScope.(page : Int) -> Unit
) {

    internal val layers: MutableList<@Composable ParallaxPagerScope.(page : Int, layer : Int) -> Unit> = mutableListOf()
    fun addParallaxLayer(content: @Composable ParallaxPagerScope.(page : Int, layer : Int) -> Unit) : ParallaxPage {
        layers.add(content)
        return this
    }
}