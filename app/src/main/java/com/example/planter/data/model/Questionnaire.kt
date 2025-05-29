package com.example.planter.data.model

import java.time.OffsetDateTime

data class QuestionnaireRequest(
    val sunlightPreference: SunlightLevel,
    val petFriendly: Boolean,
    val careLevel: Int, // от 1 до 5
    val preferredLocation: String? = null,
    val additionalPreferences: String? = null
)

data class PlantQuestionnaire(
    val id: String,
    val userId: String? = null,
    val sunlightPreference: SunlightLevel,
    val petFriendly: Boolean,
    val careLevel: Int, // от 1 до 5
    val preferredLocation: String? = null,
    val additionalPreferences: String? = null,
    val createdAt: OffsetDateTime? = null
)