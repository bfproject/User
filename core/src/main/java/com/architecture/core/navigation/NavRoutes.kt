package com.architecture.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

private const val ROUTE_USERS = "users"
private const val ROUTE_USER = "user/%s/%s/%s"
private const val ARG_USER_NAME = "userName"
private const val ARG_USER_EMAIL = "userEmail"
private const val ARG_USER_PICTURE = "userPicture"

sealed class NavRoutes(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {

    object Users : NavRoutes(ROUTE_USERS)

    object User : NavRoutes(
        route = String.format(ROUTE_USER, "{$ARG_USER_NAME}", "{$ARG_USER_EMAIL}", "{$ARG_USER_PICTURE}"),
        arguments = listOf(
            navArgument(ARG_USER_NAME) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(ARG_USER_EMAIL) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(ARG_USER_PICTURE) {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        fun routeForUser(userInput: UserInput) =
            String.format(ROUTE_USER, userInput.userName, userInput.email, userInput.picture)

        fun fromEntry(entry: NavBackStackEntry): UserInput {
            return UserInput(
                entry.arguments?.getString(ARG_USER_NAME) ?: "",
                entry.arguments?.getString(ARG_USER_EMAIL) ?: "",
                entry.arguments?.getString(ARG_USER_PICTURE) ?: "",
            )
        }

    }

}
