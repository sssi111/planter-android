package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlantQuestionnaire(
    @Json(name = "id") val id: String,
    @Json(name = "userId") val userId: String?,
    @Json(name = "sunlightPreference") val sunlightPreference: String,
    @Json(name = "petFriendly") val petFriendly: Boolean,
    @Json(name = "careLevel") val careLevel: Int,
    @Json(name = "preferredLocation") val preferredLocation: String?,
    @Json(name = "additionalPreferences") val additionalPreferences: String?,
    @Json(name = "createdAt") val createdAt: String
)