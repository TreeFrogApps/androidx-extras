# Compose Parallax Pager

Simple library that adds Parallax ViewPager functionality to jetpack compose.

### Example Usage

```kotlin
@Composable
fun Pager() {
    val pages = remember {
        val page1 = ParallaxPage(
            header = { pageIdx ->
                // composable header
            },
            footer = { pageIdx ->
                // composable footer
            },
            content = { pageIdx ->
                // composable base content
            })
            .addParallaxLayer { pageIdx, layerIdx ->
                // composable layer content
            }.addParallaxLayer { pageIdx, layerIdx ->
                // composable layer content
            }
        val page2 = ParallaxPage(
            header = { pageIdx ->
                // composable header
            },
            footer = { pageIdx ->
                // composable footer
            },
            content = { pageIdx ->
                // composable base content
            })
            .addParallaxLayer { pageIdx, layerIdx ->
                // composable layer content
            }
        listOf(page1, page2)
    }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        val state = rememberParallaxPagerState()
        HorizontalParallaxPager(
            state = state,
            effect = ParallaxEffect.Medium,
            pages = pages
        )
        HorizontalParallaxPageIndicator(
            modifier = Modifier.padding(vertical = 16.dp),
            state = state,
            pageCount = pages.size,
            pageIndicatorColor = Color.LightGray,
            activePageIndicatorColor = Color.Blue
        )
    }
}
```
