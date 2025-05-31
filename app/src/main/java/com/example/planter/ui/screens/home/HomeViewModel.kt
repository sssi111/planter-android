package com.example.planter.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.model.Plant
import com.example.planter.data.repository.PlantRepository
import com.example.planter.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.time.OffsetDateTime

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUserPlants()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun onSearch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                plantRepository.searchPlants(_uiState.value.searchQuery)
                    .collect { plants ->
                        _uiState.update { 
                            it.copy(
                                searchResults = plants,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onLocationSelected(location: String) {
        _uiState.update { it.copy(selectedLocation = location) }
        loadUserPlants()
    }
    
    fun toggleFavorite(plantId: String) {
        viewModelScope.launch {
            try {
                plantRepository.toggleFavorite(plantId)
                loadUserPlants()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to update favorite status",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun markAsWatered(plantId: String) {
        viewModelScope.launch {
            try {
                plantRepository.markAsWatered(plantId)
                loadUserPlants()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to mark plant as watered",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadUserPlants() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                plantRepository.getUserPlants()
                    .collect { plants ->
                        val now = OffsetDateTime.now()
                        val needsWatering = plants.filter { plant -> 
                            plant.nextWatering?.isBefore(now) ?: false 
                        }
                        val upcomingWatering = plants.filter { plant ->
                            val nextWatering = plant.nextWatering
                            nextWatering != null && nextWatering.isAfter(now)
                        }
                        _uiState.update { 
                            it.copy(
                                needsWateringPlants = needsWatering,
                                upcomingWateringPlants = upcomingWatering,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun MutableStateFlow<HomeUiState>.update(transform: (HomeUiState) -> HomeUiState) {
        value = transform(value)
    }
}

data class HomeUiState(
    val searchQuery: String = "",
    val searchResults: List<Plant> = emptyList(),
    val selectedLocation: String = "",
    val needsWateringPlants: List<Plant> = emptyList(),
    val upcomingWateringPlants: List<Plant> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
) 