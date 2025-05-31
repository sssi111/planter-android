package com.example.planter.ui.screens.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.api.request.QuestionnaireRequest
import com.example.planter.data.model.Plant
import com.example.planter.data.repository.RecommendationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val recommendationRepository: RecommendationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionnaireUiState())
    val uiState: StateFlow<QuestionnaireUiState> = _uiState.asStateFlow()

    fun submitQuestionnaire(request: QuestionnaireRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                recommendationRepository.saveQuestionnaire(request)
                    .collect { plant ->
                        _uiState.update { 
                            it.copy(
                                recommendedPlant = plant,
                                isLoading = false
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        error = e.message ?: "Failed to submit questionnaire",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun MutableStateFlow<QuestionnaireUiState>.update(transform: (QuestionnaireUiState) -> QuestionnaireUiState) {
        value = transform(value)
    }
}

data class QuestionnaireUiState(
    val recommendedPlant: Plant? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 