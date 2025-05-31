package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatMessage(
    @Json(name = "id") val id: String,
    @Json(name = "sessionId") val sessionId: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "role") val role: String, // "user" or "assistant"
    @Json(name = "content") val content: String,
    @Json(name = "createdAt") val createdAt: String
)