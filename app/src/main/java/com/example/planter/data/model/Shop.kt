package com.example.planter.data.model

import java.time.OffsetDateTime

data class Shop(
    val id: String,
    val name: String,
    val address: String,
    val rating: Float,
    val imageUrl: String? = null,
    val createdAt: OffsetDateTime? = null,
    val updatedAt: OffsetDateTime? = null
)