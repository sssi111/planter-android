package com.example.planter.data.repository.impl

import com.example.planter.data.api.PlanterApi
import com.example.planter.data.model.Plant
import com.example.planter.data.model.PlantQuestionnaire
import com.example.planter.data.model.QuestionnaireRequest
import com.example.planter.data.repository.RecommendationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecommendationRepositoryImpl @Inject constructor(
    private val api: PlanterApi
) : RecommendationRepository {

    override suspend fun saveQuestionnaire(request: QuestionnaireRequest): Flow<PlantQuestionnaire> = flow {
        val questionnaire = api.saveQuestionnaire(request)
        emit(questionnaire)
    }

    override suspend fun getRecommendations(questionnaireId: String): Flow<List<Plant>> = flow {
        val plants = api.getRecommendations(questionnaireId)
        emit(plants)
    }
}