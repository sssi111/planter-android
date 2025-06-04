package com.example.planter.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planter.R
import com.example.planter.data.model.Plant
import com.example.planter.ui.components.LocationSelector
import com.example.planter.ui.components.PlantCard
import com.example.planter.ui.components.SearchBar
import com.example.planter.ui.components.TopBar
import com.example.planter.ui.viewmodels.UserViewModel

@Composable
fun HomeScreen(
    onPlantClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    onNotificationsClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val userName by userViewModel.userName.collectAsState()
    val scrollState = rememberScrollState()
    
    // Handle navigation when a plant is selected from search
    LaunchedEffect(uiState.selectedPlantId) {
        uiState.selectedPlantId?.let { plantId ->
            onPlantClick(plantId)
            viewModel.clearSelectedPlant()
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        TopBar(
            userName = userName,
            avatarUrl = null,
            onNotificationsClick = onNotificationsClick
        )
        
        Spacer(Modifier.height(16.dp))
        
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            onSearch = viewModel::onSearch
        )
        
        Spacer(Modifier.height(24.dp))
        
        // Plants that need watering
        if (uiState.needsWateringPlants.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Нужен полив",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.needsWateringPlants) { plant ->
                            PlantCard(
                                plant = plant,
                                onClick = { onPlantClick(plant.id) },
                                onFavoriteClick = viewModel::toggleFavorite
                            )
                        }
                    }
                }
            }
            
            Spacer(Modifier.height(24.dp))
        }
        
        // Location selector
        LocationSelector(
            selectedLocation = uiState.selectedLocation,
            onLocationSelected = viewModel::onLocationSelected
        )
        
        Spacer(Modifier.height(24.dp))
        
        // All user's plants grouped by location
        Text(
            text = "Мои растения",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (uiState.favoritePlants.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Нет избранных растений",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Добавьте растения в избранное, чтобы увидеть их здесь",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.favoritePlants) { plant ->
                    PlantCard(
                        plant = plant,
                        onClick = { onPlantClick(plant.id) },
                        onFavoriteClick = viewModel::toggleFavorite
                    )
                }
            }
        }
        
        Spacer(Modifier.height(24.dp))
    }
} 