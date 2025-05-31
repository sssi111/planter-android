package com.example.planter.data.network

import com.example.planter.data.model.Notification
import retrofit2.http.*
import java.util.UUID

interface PlantApi {
    @GET("/notifications")
    suspend fun getNotifications(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NotificationResponse

    @POST("/notifications/{notificationId}/read")
    suspend fun markNotificationAsRead(
        @Path("notificationId") notificationId: UUID
    )
}

data class NotificationResponse(
    val notifications: List<Notification>,
    val total: Int
)