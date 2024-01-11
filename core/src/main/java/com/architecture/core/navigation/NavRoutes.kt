package com.architecture.core.navigation

private const val ROUTE_USERS = "users"

sealed class NavRoutes(
    val route: String
) {

    object Users : NavRoutes(ROUTE_USERS)

}
