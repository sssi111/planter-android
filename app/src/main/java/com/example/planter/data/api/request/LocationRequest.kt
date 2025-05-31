package com.example.planter.data.api.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationRequest(
    @Json(name = "location") val location: String
)