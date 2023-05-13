# Compose Material library

Small library for extra theming and reusable components.

# Subpackage - theme

The Android XML system has qualified resources for layouts at runtime, this made it simple
to create layout for different screen sizes based on the resources folder hierarchy for example :

```
- res
  ├── layout
  ├     └── main.xml
  ├── layout-land
  ├     └── main.xml
  ├── layout-sw600dp
  ├     └── main.xml
  └── layout-sw600dp-land
        └── main.xml
```

Jetpack Compose has the concept of qualified resources for `layout` resources and has no inbuilt
was to determine current screen bucket size. This base project includes :

---

`windowSize`

which includes size definitions of :

    SmallPortrait,
    SmallLandscape,
    MediumPortrait,
    MediumLandscape,
    LargePortrait,
    LargeLandscape;

---

`dimens`

which includes dimen definitions

---

Both of these are available to any composable at runtime and are very simple implementations that
utilise `androidx.compose.runtime.CompositionLocalProvider` to provide these values at runtime.  
This is **NOT** a replacement for the xml qualified system but allows rationale for different screen
layouts at runtime based on screen size for example showing two pane on landscape only :

### Example Usage

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialThemeExtended(windowSize = rememberWindowSize()) {
                SingleOrTwoPane(isTwoPane = windowSize.isLandscape())
            }
        }
    }
}

@Composable
fun SingleOrTwoPane(isTwoPane: Boolean) {
    if (isTwoPane) {
        // show two pane
    } else {
        // show single pane
    }
}
```

# Subpackage - swipeable

Collection of swipeable components
