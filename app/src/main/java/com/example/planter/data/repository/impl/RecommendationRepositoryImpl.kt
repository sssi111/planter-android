package com.example.planter.data.repository.impl

import com.example.planter.data.api.PlanterApi
import com.example.planter.data.model.Plant
import com.example.planter.data.model.PlantQuestionnaire
import com.example.planter.data.api.request.QuestionnaireRequest
import com.example.planter.data.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val api: PlanterApi
) : RecommendationRepository {

    override suspend fun saveQuestionnaire(request: QuestionnaireRequest): Flow<PlantQuestionnaire> = flow {
        val response = api.submitQuestionnaire(request)
        if (response.isSuccessful) {
            response.body()?.let { emit(it) } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Failed to save questionnaire: ${response.code()}")
        }
    }

    override suspend fun getRecommendations(questionnaireId: String): Flow<List<Plant>> = flow {
        val plants = api.getRecommendations(questionnaireId)
        emit(plants)
    }
}