package com.treefrogapps.androidx.compose.pager

import androidx.compose.runtime.Composable

/**
 * Class to present a Parallax page, use this class to build pages
 */
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