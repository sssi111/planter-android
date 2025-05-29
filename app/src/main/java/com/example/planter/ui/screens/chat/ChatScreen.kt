package com.example.planter.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planter.R
import com.example.planter.ui.components.TopBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier
) {
    var message by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        TopBar(
            userName = "User", // TODO: заменить на имя пользователя
            avatarUrl = null,
            onNotificationsClick = { }
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.title_notifications),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))
        // TODO: Список сообщений
        Box(modifier = Modifier.weight(1f)) {
            Text(
                text = "Your chat messages will appear here",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = { Text(stringResource(R.string.type_message)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { /* TODO: отправить сообщение */ }) {
                    Icon(Icons.Default.Send, contentDescription = stringResource(R.string.send))
                }
            }
        )
        Spacer(Modifier.height(8.dp))
    }
} 