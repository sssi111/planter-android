package com.example.planter.data.api.adapter

import com.example.planter.data.model.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class LanguageAdapter {
    @FromJson
    fun fromJson(value: String): Language = when (value) {
        "RUSSIAN" -> Language.RUSSIAN
        "ENGLISH" -> Language.ENGLISH
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
    fun fromJson(value: String): SunlightLevel = when (value) {
        "LOW" -> SunlightLevel.LOW
        "MEDIUM" -> SunlightLevel.MEDIUM
        "HIGH" -> SunlightLevel.HIGH
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
    fun fromJson(value: String): HumidityLevel = when (value) {
        "LOW" -> HumidityLevel.LOW
        "MEDIUM" -> HumidityLevel.MEDIUM
        "HIGH" -> HumidityLevel.HIGH
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
    fun fromJson(value: String): NotificationType = when (value) {
        "WATERING" -> NotificationType.WATERING
        else -> throw IllegalArgumentException("Unknown NotificationType: $value")
    }

    @ToJson
    fun toJson(type: NotificationType): String = when (type) {
        NotificationType.WATERING -> "WATERING"
    }
} 