package com.treefrogapps.androidx.compose.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import kotlin.properties.Delegates

/**
 * Edge to edge compose component activity is a Template method pattern that automatically sets [ComponentActivity.enableEdgeToEdge]
 * and has mandated abstract a hook function to provide the composable content.
 *
 * Clients can override default status and navigation [SystemBarStyle] initial values. In addition clients can also
 * update [ComponentActivity.enableEdgeToEdge] with new status and navigation [SystemBarStyle] values and this will update the scrim.
 *
 * @constructor Create empty Edge to edge compose component activity
 */
abstract class EdgeToEdgeComposeComponentActivity : ComponentActivity() {

    /**
     * Status bar style initial value - clients can override to provide a different initial value.
     */
    protected open val statusBarStyleInitial = SystemBarStyle.auto(
        lightScrim = Color.TRANSPARENT,
        darkScrim = Color.TRANSPARENT
    )

    /**
     * Status bar style - client can set new values, which will then update in a reactive fashion.
     *
     * Clients MUST set new [SystemBarStyle] after Lifecycle.State.CREATED
     */
    var statusBarStyle: SystemBarStyle by Delegates.observable(
        initialValue = statusBarStyleInitial,
        onChange = { _, _, newValue ->
            updateEdgeToEdgeIfCreated(
                statusBarStyle = newValue,
                navigationBarStyle = navigationBarStyle
            )
        })

    /**
     * Navigation bar style initial - clients can override to provide a different initial value.
     */
    protected open val navigationBarStyleInitial = SystemBarStyle.light(
        scrim = Color.TRANSPARENT,
        darkScrim = Color.TRANSPARENT
    )

    /**
     * Navigation bar styl e- client can set new values, which will then update in a reactive fashion.
     *
     * Clients MUST set new [SystemBarStyle] after Lifecycle.State.CREATED
     */
    var navigationBarStyle: SystemBarStyle by Delegates.observable(
        initialValue = navigationBarStyleInitial,
        onChange = { _, _, newValue ->
            updateEdgeToEdgeIfCreated(
                statusBarStyle = statusBarStyle,
                navigationBarStyle = newValue
            )
        })

    /**
     * Provide the composable content to display
     *
     * Content must be overridden by concrete subtypes.
     */
    protected abstract fun content() : @Composable (() -> Unit)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = statusBarStyle,
            navigationBarStyle = navigationBarStyle
        )
        super.onCreate(savedInstanceState)
        setContent(content = content())
    }

    private fun updateEdgeToEdgeIfCreated(statusBarStyle: SystemBarStyle, navigationBarStyle: SystemBarStyle) {
        if (lifecycle.currentState.isAtLeast(state = Lifecycle.State.CREATED)) {
            enableEdgeToEdge(
                statusBarStyle = statusBarStyle,
                navigationBarStyle = navigationBarStyle
            )
        }
    }
}
