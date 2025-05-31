package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "profileImageUrl") val profileImageUrl: String? = null,
    @Json(name = "locations") val locations: List<String> = emptyList(),
    @Json(name = "favoritePlantIds") val favoritePlantIds: List<String> = emptyList(),
    @Json(name = "ownedPlantIds") val ownedPlantIds: List<String> = emptyList(),
    @Json(name = "language") val language: Language = Language.RUSSIAN,
    @Json(name = "notificationsEnabled") val notificationsEnabled: Boolean = true,
    @Json(name = "createdAt") val createdAt: OffsetDateTime? = null,
    @Json(name = "updatedAt") val updatedAt: OffsetDateTime? = null
)

@JsonClass(generateAdapter = false)
enum class Language {
    @Json(name = "RUSSIAN") RUSSIAN,
    @Json(name = "ENGLISH") ENGLISH
}