package com.example.planter.data.model

import java.time.OffsetDateTime

data class Plant(
    val id: String,
    val name: String,
    val scientificName: String,
    val description: String,
    val imageUrl: String,
    val careInstructions: CareInstructions,
    val price: Double? = null,
    val shopId: String? = null,
    val isFavorite: Boolean = false,
    val location: String? = null,
    val lastWatered: OffsetDateTime? = null,
    val nextWatering: OffsetDateTime? = null,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)

data class CareInstructions(
    val wateringFrequency: Int, // in days
    val sunlight: SunlightLevel,
    val temperature: TemperatureRange,
    val humidity: HumidityLevel,
    val soilType: String,
    val fertilizerFrequency: Int, // in days
    val additionalNotes: String
)

enum class SunlightLevel {
    LOW, MEDIUM, HIGH
}

data class TemperatureRange(
    val min: Int,
    val max: Int
)

enum class HumidityLevel {
    LOW, MEDIUM, HIGH
} 