package com.example.planter.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.model.Plant
import com.example.planter.data.model.SpecialOffer
import com.example.planter.data.repository.PlantRepository
import com.example.planter.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val plantRepository: PlantRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadPlants()
        loadSpecialOffers()
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
        loadPlants()
    }
    
    fun toggleFavorite(plantId: String) {
        viewModelScope.launch {
            try {
                plantRepository.toggleFavorite(plantId)
                // Reload plants to reflect the updated favorite status
                loadPlants()
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

    private fun loadUserData() {
        viewModelScope.launch {
            userRepository.getCurrentUser()
                .collect { user ->
                    _uiState.update { 
                        it.copy(
                            userName = user?.name ?: "",
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun loadPlants() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                plantRepository.getAllPlants()
                    .collect { plants ->
                        _uiState.update { 
                            it.copy(
                                plants = plants,
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

    private fun loadSpecialOffers() {
        // TODO: Implement special offers loading
        _uiState.update { 
            it.copy(
                specialOffers = emptyList(),
                isLoading = false
            )
        }
    }
}

data class HomeUiState(
    val userName: String = "",
    val searchQuery: String = "",
    val searchResults: List<Plant> = emptyList(),
    val specialOffers: List<SpecialOffer> = emptyList(),
    val selectedLocation: String = "",
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
) 