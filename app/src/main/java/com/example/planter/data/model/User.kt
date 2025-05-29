package com.example.planter.data.model

import java.time.OffsetDateTime

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImageUrl: String? = null,
    val locations: List<String> = emptyList(),
    val favoritePlantIds: List<String> = emptyList(),
    val ownedPlantIds: List<String> = emptyList(),
    val language: Language = Language.RUSSIAN,
    val notificationsEnabled: Boolean = true,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)

enum class Language {
    RUSSIAN, ENGLISH
}