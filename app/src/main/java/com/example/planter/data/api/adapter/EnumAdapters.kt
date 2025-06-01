package com.example.planter.data.api.adapter

import com.example.planter.data.model.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class LanguageAdapter {
    @FromJson
    fun fromJson(value: String): Language = when {
        value.isBlank() -> Language.RUSSIAN // Default value for empty strings
        value == "RUSSIAN" -> Language.RUSSIAN
        value == "ENGLISH" -> Language.ENGLISH
        else -> throw IllegalArgumentException("Unknown Language: $value")
    }

    @ToJson
    fun toJson(language: Language): String = when (language) {
        Language.RUSSIAN -> "RUSSIAN"
        Language.ENGLISH -> "ENGLISH"
    }
}

class SunlightLevelAdapter {
    @FromJson
    fun fromJson(value: String): SunlightLevel = when {
        value.isBlank() -> SunlightLevel.MEDIUM // Default value for empty strings
        value == "LOW" -> SunlightLevel.LOW
        value == "MEDIUM" -> SunlightLevel.MEDIUM
        value == "HIGH" -> SunlightLevel.HIGH
        else -> throw IllegalArgumentException("Unknown SunlightLevel: $value")
    }

    @ToJson
    fun toJson(level: SunlightLevel): String = when (level) {
        SunlightLevel.LOW -> "LOW"
        SunlightLevel.MEDIUM -> "MEDIUM"
        SunlightLevel.HIGH -> "HIGH"
    }
}

class HumidityLevelAdapter {
    @FromJson
    fun fromJson(value: String): HumidityLevel = when {
        value.isBlank() -> HumidityLevel.MEDIUM // Default value for empty strings
        value == "LOW" -> HumidityLevel.LOW
        value == "MEDIUM" -> HumidityLevel.MEDIUM
        value == "HIGH" -> HumidityLevel.HIGH
        else -> throw IllegalArgumentException("Unknown HumidityLevel: $value")
    }

    @ToJson
    fun toJson(level: HumidityLevel): String = when (level) {
        HumidityLevel.LOW -> "LOW"
        HumidityLevel.MEDIUM -> "MEDIUM"
        HumidityLevel.HIGH -> "HIGH"
    }
}

class NotificationTypeAdapter {
    @FromJson
    fun fromJson(value: String): NotificationType = when {
        value.isBlank() -> NotificationType.WATERING // Default value for empty strings
        value == "WATERING" -> NotificationType.WATERING
        else -> throw IllegalArgumentException("Unknown NotificationType: $value")
    }

    @ToJson
    fun toJson(type: NotificationType): String = when (type) {
        NotificationType.WATERING -> "WATERING"
    }
} 