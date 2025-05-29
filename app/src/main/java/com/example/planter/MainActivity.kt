package com.example.planter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.planter.navigation.NavGraph
import com.example.planter.navigation.Screen
import com.example.planter.ui.theme.PlanterTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

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
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        onPlantClick = { plantId ->
                            navController.navigate(Screen.PlantDetails.createRoute(plantId))
                        }
                    )
                }
            }
        }
    }
}

// BottomBar и BottomBarItem перенесены в отдельный файл BottomBar.kt
