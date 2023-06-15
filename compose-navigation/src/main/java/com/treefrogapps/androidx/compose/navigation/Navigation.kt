package com.treefrogapps.androidx.compose.navigation

import androidx.navigation.NavHostController

/**
 * Basic Navigation Actions
 */
interface NavigateActions {

    fun popTo(controller: NavHostController): Boolean

    fun navigateTo(controller: NavHostController)

    fun navigateToPoppingBackStack(controller: NavHostController)
}

/**
 * Extended Navigation Actions, including a [NavArg]
 */
interface NavigateWithArgActions<NavArg> : NavigateActions {

    fun navArg(controller: NavHostController): NavArg?

    fun navigateTo(navArg: NavArg, controller: NavHostController)

    fun navigateToPoppingBackStack(navArg: NavArg, controller: NavHostController)

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

    override fun navigateToPoppingBackStack(controller: NavHostController) {
        navigatePoppingBackStack(navController = controller, destinationRoute = route)
    }
}

/**
 * Navigation Facade to assist Jetpack Compose Navigation.
 *
 * This uses [androidx.lifecycle.SavedStateHandle] to
 */
abstract class NavigationDestinationWithArgument<NavArg>(
    route: String,
) : NavigationDestination(route), NavigateWithArgActions<NavArg> {

    override fun navArg(controller: NavHostController): NavArg? =
        controller.previousBackStackEntry?.savedStateHandle?.get<NavArg>(route)

    override fun navigateTo(navArg: NavArg, controller: NavHostController) {
        with(controller) {
            currentBackStackEntry?.savedStateHandle?.set(route, navArg)
            navigate(route) {
                launchSingleTop
            }
        }
    }

    override fun navigateToPoppingBackStack(navArg: NavArg, controller: NavHostController) {
        controller.currentBackStackEntry?.savedStateHandle?.set(route, navArg)
        navigatePoppingBackStack(navController = controller, destinationRoute = route)
    }
}

fun navigationDestinationOf(route: String): NavigationDestination = object : NavigationDestination(route) {}

fun <NavArg> navigationDestinationWithArgumentOf(route: String): NavigationDestinationWithArgument<NavArg> = object : NavigationDestinationWithArgument<NavArg>(route) {}

private fun navigatePoppingBackStack(
    navController: NavHostController,
    destinationRoute: String
) {
    val currRoute = navController.currentDestination?.route
    navController.navigate(destinationRoute) {
        currRoute?.run {
            popUpTo(this) {
                inclusive = true
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

