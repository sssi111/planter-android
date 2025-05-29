package com.example.planter.data.model

data class SpecialOffer(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val discountPercentage: Int,
    val validUntil: Long
) 