package com.example.planter.data.repository

import com.example.planter.data.model.Plant
import com.example.planter.data.model.PlantQuestionnaire
import com.example.planter.data.api.request.QuestionnaireRequest
import kotlinx.coroutines.flow.Flow

interface RecommendationRepository {
    suspend fun saveQuestionnaire(request: QuestionnaireRequest): Flow<Plant>
    suspend fun getRecommendations(questionnaireId: String): Flow<List<Plant>>
}