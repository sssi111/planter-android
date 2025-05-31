package com.example.planter.data.api.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionnaireRequest(
    @Json(name = "sunlightPreference") val sunlightPreference: String,
    @Json(name = "petFriendly") val petFriendly: Boolean,
    @Json(name = "careLevel") val careLevel: Int,
    @Json(name = "preferredLocation") val preferredLocation: String?,
    @Json(name = "additionalPreferences") val additionalPreferences: Map<String, String>?
)