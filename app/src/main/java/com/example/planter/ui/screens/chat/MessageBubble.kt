package com.example.planter.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planter.data.model.ChatMessage
import java.util.*

@Composable
fun MessageBubble(
    message: ChatMessage,
    isUser: Boolean
) {
    val bubbleColor = if (isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val alignment = if (isUser) {
        androidx.compose.ui.Alignment.End
    } else {
        androidx.compose.ui.Alignment.Start
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = if (isUser) androidx.compose.ui.Alignment.CenterEnd else androidx.compose.ui.Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bubbleColor)
                .padding(12.dp),
            horizontalAlignment = if (isUser) androidx.compose.ui.Alignment.End else androidx.compose.ui.Alignment.Start
        ) {
            Text(
                text = message.content,
                color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = message.createdAt.substring(11, 16), // Show just time
                style = MaterialTheme.typography.labelSmall,
                color = if (isUser) Color.White.copy(alpha = 0.7f) 
                       else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun MessageBubblePreview() {
    MessageBubble(
        message = ChatMessage(
            id = UUID.randomUUID().toString(),
            sessionId = "123",
            userId = "user1",
            role = "user",
            content = "Hello, how are you?",
            createdAt = "2023-01-01T12:34:56Z"
        ),
        isUser = true
    )
}