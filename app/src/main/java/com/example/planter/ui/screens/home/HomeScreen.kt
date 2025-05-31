package com.example.planter.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.planter.R
import com.example.planter.ui.components.LocationSelector
import com.example.planter.ui.components.OfferCard
import com.example.planter.ui.components.PlantCard
import com.example.planter.ui.components.SearchBar
import com.example.planter.ui.components.TopBar

@Composable
fun HomeScreen(
    onPlantClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        TopBar(
            userName = uiState.userName,
            avatarUrl = null, // TODO: добавить url аватара пользователя
            onNotificationsClick = { /* TODO */ }
        )
        Spacer(Modifier.height(16.dp))
        SearchBar(
            query = uiState.searchQuery,
            onQueryChange = viewModel::onSearchQueryChange,
            onSearch = viewModel::onSearch
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.special_offers),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.specialOffers) { offer ->
                OfferCard(offer = offer)
            }
        }
        Spacer(Modifier.height(16.dp))
        LocationSelector(
            selectedLocation = uiState.selectedLocation,
            onLocationSelected = viewModel::onLocationSelected
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.my_plants),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uiState.plants) { plant ->
                PlantCard(
                    plant = plant,
                    onClick = { onPlantClick(plant.id) },
                    onFavoriteClick = viewModel::toggleFavorite
                )
            }
        }
    }
} 