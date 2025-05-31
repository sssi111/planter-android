package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Plant(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "scientificName") val scientificName: String,
    @Json(name = "description") val description: String,
    @Json(name = "imageUrl") val imageUrl: String,
    @Json(name = "careInstructions") val careInstructions: CareInstructions,
    @Json(name = "price") val price: Double? = null,
    @Json(name = "shopId") val shopId: String? = null,
    @Json(name = "isFavorite") val isFavorite: Boolean = false,
    @Json(name = "location") val location: String? = null,
    @Json(name = "lastWatered") val lastWatered: OffsetDateTime? = null,
    @Json(name = "nextWatering") val nextWatering: OffsetDateTime? = null,
    @Json(name = "createdAt") val createdAt: OffsetDateTime? = null,
    @Json(name = "updatedAt") val updatedAt: OffsetDateTime? = null
)

@JsonClass(generateAdapter = true)
data class CareInstructions(
    @Json(name = "wateringFrequency") val wateringFrequency: Int, // in days
    @Json(name = "sunlight") val sunlight: SunlightLevel,
    @Json(name = "temperature") val temperature: TemperatureRange,
    @Json(name = "humidity") val humidity: HumidityLevel,
    @Json(name = "soilType") val soilType: String,
    @Json(name = "fertilizerFrequency") val fertilizerFrequency: Int, // in days
    @Json(name = "additionalNotes") val additionalNotes: String
)

@JsonClass(generateAdapter = false)
enum class SunlightLevel {
    @Json(name = "LOW") LOW,
    @Json(name = "MEDIUM") MEDIUM,
    @Json(name = "HIGH") HIGH
}

@JsonClass(generateAdapter = true)
data class TemperatureRange(
    @Json(name = "min") val min: Int,
    @Json(name = "max") val max: Int
)

@JsonClass(generateAdapter = false)
enum class HumidityLevel {
    @Json(name = "LOW") LOW,
    @Json(name = "MEDIUM") MEDIUM,
    @Json(name = "HIGH") HIGH
}