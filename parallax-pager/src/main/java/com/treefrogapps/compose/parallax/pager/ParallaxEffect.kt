package com.treefrogapps.compose.parallax.pager

enum class ParallaxEffect private constructor(val amount : Float){
    Subtle(amount = 0.05F),
    Normal(amount = 0.1F),
    Evident(amount = 0.25F)
}