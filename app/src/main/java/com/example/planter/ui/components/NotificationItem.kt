package com.example.planter.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.planter.data.model.Notification
import com.example.planter.data.model.NotificationType
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun NotificationItem(
    notification: Notification,
    onNotificationClick: (Notification) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNotificationClick(notification) }
            .background(
                if (!notification.isRead)
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                else
                    MaterialTheme.colorScheme.surface
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка типа уведомления
            when (notification.type) {
                NotificationType.WATERING -> {
                    Icon(
                        imageVector = Icons.Filled.WaterDrop,
                        contentDescription = "Уведомление о поливе",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Содержимое уведомления
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.message,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = notification.createdAt.format(
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Индикатор непрочитанного уведомления
            if (!notification.isRead) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = "Непрочитанное уведомление",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(8.dp)
                )
            }
        }
    }
} 