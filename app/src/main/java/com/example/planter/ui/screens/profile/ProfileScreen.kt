package com.example.planter.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.planter.R
import com.example.planter.ui.components.TopBar

@Composable
fun ProfileScreen() {
    // TODO: Получить данные пользователя из ViewModel
    Column(
        modifier = Modifier
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
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Profile information will appear here", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
} 