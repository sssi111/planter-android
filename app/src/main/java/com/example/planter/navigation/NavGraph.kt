package com.example.planter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planter.ui.screens.auth.AuthViewModel
import com.example.planter.ui.screens.auth.LoginScreen
import com.example.planter.ui.screens.auth.RegisterScreen
import com.example.planter.ui.screens.chat.ChatScreen
import com.example.planter.ui.screens.favorites.FavoritesScreen
import com.example.planter.ui.screens.home.HomeScreen
import com.example.planter.ui.screens.plant.PlantDetailsScreen
import com.example.planter.ui.screens.profile.ProfileScreen
import com.example.planter.ui.screens.shop.ShopScreen
import com.example.planter.ui.screens.recommendations.QuestionnaireScreen
import com.example.planter.ui.screens.notifications.NotificationsScreen
import com.example.planter.data.model.PlantQuestionnaire

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Chat : Screen("chat")
    object Shop : Screen("shop")
    object Profile : Screen("profile")
    object Notifications : Screen("notifications")
    object PlantDetails : Screen("plant_details/{plantId}") {
        fun createRoute(plantId: String) = "plant_details/$plantId"
    }
    object QuestionnaireScreen : Screen("questionnaire")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onPlantClick: (String) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    // Определяем начальный экран в зависимости от статуса авторизации
    val startDestination = if (isLoggedIn) Screen.Home.route else Screen.Login.route
    
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
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
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Screen.QuestionnaireScreen.route) {
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
                onPlantClick = onPlantClick,
                onNotificationsClick = {
                    navController.navigate(Screen.Notifications.route)
                }
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
            ShopScreen()
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen()
        }

        composable(
            Screen.PlantDetails.route,
            arguments = listOf(
                navArgument("plantId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId") ?: return@composable
            PlantDetailsScreen(
                plantId = plantId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.QuestionnaireScreen.route) {
            QuestionnaireScreen(
                navController = navController,
                onQuestionnaireComplete = { questionnaire: PlantQuestionnaire ->
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.QuestionnaireScreen.route) { inclusive = true }
                    }
                }
            )
        }
    }
} 