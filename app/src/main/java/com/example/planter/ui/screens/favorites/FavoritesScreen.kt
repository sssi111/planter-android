package com.example.planter.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planter.R
import com.example.planter.ui.components.PlantCard
import com.example.planter.ui.components.TopBar
import androidx.compose.foundation.background

@Composable
fun FavoritesScreen(
    onPlantClick: (String) -> Unit
) {
    // TODO: Получить список избранных растений из ViewModel
    val favoritePlants = remember { listOf<com.example.planter.data.model.Plant>() } // заменить на state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        TopBar(
            userName = "User", // TODO: заменить на имя пользователя
            avatarUrl = null,
            onNotificationsClick = { }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.favorites),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))
        if (favoritePlants.isEmpty()) {
            Text(
                text = "Your favorite plants will appear here",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoritePlants) { plant ->
                    PlantCard(
                        plant = plant,
                        onClick = { onPlantClick(plant.id) }
                    )
                }
            }
        }
    }
} 