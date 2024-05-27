package com.treefrogapps.androidx.compose.ui.tooling.preview

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Repeatable
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.PHONE
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.PHONE
)
annotation class DayNightPreview

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Repeatable
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.FOLDABLE
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.FOLDABLE
)
annotation class DayNightFoldablePreview

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@Repeatable
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.TABLET
)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.TABLET
)
annotation class DayNightTabletPreview

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION
)
@DayNightPreview
@DayNightFoldablePreview
@DayNightTabletPreview
annotation class DayNightDevicesPreview