@GET("/notifications")
suspend fun getNotifications(
    @Query("page") page: Int,
    @Query("pageSize") pageSize: Int
): NotificationResponse

@POST("/notifications/{notificationId}/read")
suspend fun markNotificationAsRead(
    @Path("notificationId") notificationId: UUID
)

data class NotificationResponse(
    val notifications: List<Notification>,
    val total: Int
) 