package com.treefrogapps.androidx.compose.pager

/**
 * Class to denote the type of parallax effect
 *
 * [Overlay] Will center each layer
 * [Stacked] Will vertically stack each layer
 * [Aligned] Will center and align each layer to the bottom
 */
enum class ParallaxMode {
    Overlay, Stacked, Aligned
}