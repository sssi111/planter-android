package com.example.planter.ui.screens.plant

import androidx.lifecycle.SavedStateHandle
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
class PlantDetailsViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlantDetailsUiState())
    val uiState: StateFlow<PlantDetailsUiState> = _uiState.asStateFlow()

    private val plantId: String = checkNotNull(savedStateHandle["plantId"])

    init {
        loadPlantDetails()
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            try {
                plantRepository.toggleFavorite(plantId)
                // Reload plant details to reflect the updated favorite status
                loadPlantDetails()
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

    fun markAsWatered() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                plantRepository.markAsWatered(plantId)
                    .collect { plant ->
                        _uiState.update { 
                            it.copy(
                                plant = plant,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Failed to mark as watered",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadPlantDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            plantRepository.getPlantById(plantId)
                .catch { e ->
                    _uiState.update { 
                        it.copy(
                            error = e.message ?: "Failed to load plant details",
                            isLoading = false
                        )
                    }
                }
                .collect { plant ->
                    _uiState.update { 
                        it.copy(
                            plant = plant,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun MutableStateFlow<PlantDetailsUiState>.update(transform: (PlantDetailsUiState) -> PlantDetailsUiState) {
        value = transform(value)
    }
}

data class PlantDetailsUiState(
    val plant: Plant? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)