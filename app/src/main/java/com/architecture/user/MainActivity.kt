package com.architecture.user

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architecture.core.navigation.NavRoutes
import com.architecture.feature_users.list.UserListScreen
import com.architecture.feature_users.single.UserScreen
import com.architecture.user.ui.theme.UserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(navController, startDestination = NavRoutes.Users.route) {
        composable(route = NavRoutes.Users.route) {
            UserListScreen(hiltViewModel(), navController)
        }
        composable(
            route = NavRoutes.User.route,
            arguments = NavRoutes.User.arguments
        ) {
            UserScreen(hiltViewModel(), NavRoutes.User.fromEntry(it))
        }
    }
}
