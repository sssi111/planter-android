package com.example.planter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planter.ui.screens.auth.LoginScreen
import com.example.planter.ui.screens.auth.RegisterScreen
import com.example.planter.ui.screens.chat.ChatScreen
import com.example.planter.ui.screens.favorites.FavoritesScreen
import com.example.planter.ui.screens.home.HomeScreen
import com.example.planter.ui.screens.plant.PlantDetailsScreen
import com.example.planter.ui.screens.profile.ProfileScreen
import com.example.planter.ui.screens.shop.ShopScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Chat : Screen("chat")
    object Shop : Screen("shop")
    object Profile : Screen("profile")
    object PlantDetails : Screen("plant_details/{plantId}") {
        fun createRoute(plantId: String) = "plant_details/$plantId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onPlantClick: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = { email, password ->
                    // TODO: Implement login logic
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterClick = { name, email, password ->
                    // TODO: Implement register logic
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onPlantClick = onPlantClick
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onPlantClick = onPlantClick
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen()
        }

        composable(Screen.Shop.route) {
            ShopScreen(
                onPlantClick = onPlantClick
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(
            route = Screen.PlantDetails.route,
            arguments = listOf(navArgument("plantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId") ?: return@composable
            PlantDetailsScreen(
                plantId = plantId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
} 