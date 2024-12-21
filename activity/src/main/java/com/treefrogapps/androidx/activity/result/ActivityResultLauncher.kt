package com.treefrogapps.androidx.activity.result

import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat


fun ActivityResultLauncher<Unit>.launch(options: ActivityOptionsCompat? = null) = launch(input = Unit, options = options)
