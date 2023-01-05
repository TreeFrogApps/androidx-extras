package com.treefrogapps.androidx.compose.pager

/**
 * Class to denote the strength of the parallax effect
 */
enum class ParallaxEffect(val amount : Float){
    Small(amount = 0.05F),
    Medium(amount = 0.1F),
    Large(amount = 0.25F)
}