package com.example.planter.data.repository

import com.example.planter.data.model.Notification
import com.example.planter.data.network.PlantApi
import com.example.planter.data.network.NotificationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val api: PlantApi
) {
    fun getNotifications(page: Int = 1, pageSize: Int = 10): Flow<List<Notification>> = flow {
        val response = api.getNotifications(page, pageSize)
        emit(response.notifications)
    }

    suspend fun markAsRead(notificationId: UUID) {
        api.markNotificationAsRead(notificationId)
    }
} 