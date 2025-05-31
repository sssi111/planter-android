package com.example.planter.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.model.Plant
import com.example.planter.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavoritePlants()
    }

    fun toggleFavorite(plantId: String) {
        viewModelScope.launch {
            try {
                plantRepository.toggleFavorite(plantId)
                // Reload favorite plants to reflect the updated favorite status
                loadFavoritePlants()
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

    private fun loadFavoritePlants() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            plantRepository.getFavoritePlants()
                .catch { e ->
                    _uiState.update { 
                        it.copy(
                            error = e.message ?: "Failed to load favorite plants",
                            isLoading = false
                        )
                    }
                }
                .collect { plants ->
                    _uiState.update { 
                        it.copy(
                            plants = plants,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun MutableStateFlow<FavoritesUiState>.update(transform: (FavoritesUiState) -> FavoritesUiState) {
        value = transform(value)
    }
}

data class FavoritesUiState(
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)