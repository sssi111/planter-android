package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Questionnaire(
    @Json(name = "sunlightPreference") val sunlightPreference: SunlightLevel,
    @Json(name = "petFriendly") val petFriendly: Boolean,
    @Json(name = "careLevel") val careLevel: Int, // от 1 до 5
    @Json(name = "preferredLocation") val preferredLocation: String? = null,
    @Json(name = "additionalPreferences") val additionalPreferences: String? = null
)
