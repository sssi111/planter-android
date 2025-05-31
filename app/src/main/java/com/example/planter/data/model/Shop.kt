package com.example.planter.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Shop(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "address") val address: String,
    @Json(name = "rating") val rating: Float,
    @Json(name = "imageUrl") val imageUrl: String? = null,
    @Json(name = "createdAt") val createdAt: OffsetDateTime? = null,
    @Json(name = "updatedAt") val updatedAt: OffsetDateTime? = null
)