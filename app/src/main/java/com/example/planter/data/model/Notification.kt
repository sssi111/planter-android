package com.example.planter.data.model

import java.time.OffsetDateTime
import java.util.UUID

data class Notification(
    val id: UUID,
    val userId: UUID,
    val plantId: UUID,
    val type: NotificationType,
    val message: String,
    val isRead: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime,
    val plant: Plant
)

enum class NotificationType {
    WATERING
} 