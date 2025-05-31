package com.example.planter.data.api.request

import com.example.planter.data.model.Questionnaire
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionnaireRequest(
    @Json(name = "questionnaire") val questionnaire: Questionnaire
)