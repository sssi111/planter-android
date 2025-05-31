package com.example.planter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.planter.navigation.NavGraph
import com.example.planter.navigation.Screen
import com.example.planter.ui.components.BottomBar
import com.example.planter.ui.screens.auth.AuthViewModel
import com.example.planter.ui.theme.PlanterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val authViewModel: AuthViewModel = hiltViewModel()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    
    // Determine if we should show the bottom bar
    val showBottomBar = when (currentRoute) {
        Screen.Login.route, Screen.Register.route, Screen.QuestionnaireScreen.route -> false
        else -> true
    }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        selectedTabIndex = index
                        val route = when (index) {
                            0 -> Screen.Home.route
                            1 -> Screen.Favorites.route
                            2 -> Screen.Chat.route
                            3 -> Screen.Shop.route
                            4 -> Screen.Profile.route
                            else -> Screen.Home.route
                        }
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavGraph(
                navController = navController,
                onPlantClick = { plantId ->
                    navController.navigate(Screen.PlantDetails.createRoute(plantId))
                },
                authViewModel = authViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
