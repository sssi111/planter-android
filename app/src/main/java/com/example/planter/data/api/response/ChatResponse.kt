package com.example.planter.data.api.response

import com.example.planter.data.model.ChatMessage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatResponse(
    @Json(name = "message") val message: ChatMessage
)