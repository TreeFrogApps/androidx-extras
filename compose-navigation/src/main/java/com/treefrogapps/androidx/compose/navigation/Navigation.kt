package com.treefrogapps.androidx.compose.navigation

import androidx.navigation.NavHostController

/**
 * Basic Navigation Actions
 */
interface NavigateActions {

    fun popTo(controller: NavHostController): Boolean

    fun navigateTo(controller: NavHostController)
}

/**
 * Extended Navigation Actions, including a [NavArg]
 */
interface NavigateWithArgActions<NavArg> : NavigateActions {

    fun navArg(): NavArg?

    fun navigateTo(navArg: NavArg, controller: NavHostController)
}

/**
 * Navigation Facade to assist Jetpack Compose Navigation.
 */
abstract class NavigationDestination(
    val route: String
) : NavigateActions {

    override fun popTo(controller: NavHostController): Boolean =
        controller.popBackStack(route = route, inclusive = false)

    override fun navigateTo(controller: NavHostController) =
        controller.navigate(route)
}

/**
 * Navigation Facade to assist Jetpack Compose Navigation.
 *
 * This is a very basic implementation that uses a [Map] object to
 * store and retrieve [NavArg].  This will mean on process death that
 * [NavArg] will NOT be restored.
 */
abstract class NavigationDestinationWithArgument<NavArg>(
    route: String,
) : NavigationDestination(route), NavigateWithArgActions<NavArg> {

    companion object {
        private val argumentStore: MutableMap<String, Any?> = mutableMapOf()
    }

    @Suppress("UNCHECKED_CAST")
    override fun navArg(): NavArg? =
        argumentStore[route] as? NavArg?

    override fun navigateTo(navArg: NavArg, controller: NavHostController) {
        argumentStore[route] = navArg
        controller.navigate(route)
    }
}

fun navigationDestinationOf(route : String) : NavigationDestination = object : NavigationDestination(route) {}

fun <NavArg> navigationDestinationWithArgumentOf(route : String) : NavigationDestinationWithArgument<NavArg> = object : NavigationDestinationWithArgument<NavArg>(route) {}

