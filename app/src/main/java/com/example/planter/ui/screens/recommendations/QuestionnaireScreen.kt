package com.example.planter.ui.screens.recommendations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.planter.R
import com.example.planter.data.model.PlantQuestionnaire
import com.example.planter.navigation.Screen
import com.example.planter.data.api.request.QuestionnaireRequest

@Composable
fun QuestionnaireScreen(
    onPlantRecommended: (String) -> Unit,
    viewModel: QuestionnaireViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Handle navigation when a plant is recommended
    LaunchedEffect(uiState.recommendedPlant) {
        uiState.recommendedPlant?.let { plant ->
            onPlantRecommended(plant.id)
        }
    }

    var sunlightPreference by remember { mutableStateOf("") }
    var petFriendly by remember { mutableStateOf(false) }
    var careLevel by remember { mutableStateOf(1) }
    var preferredLocation by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Предпочтения по растениям",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sunlight preference selection
        Text("Предпочтения по освещению", fontWeight = FontWeight.Medium)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf(
                "Низкое" to "Low",
                "Среднее" to "Medium",
                "Высокое" to "High"
            ).forEach { (displayText, value) ->
                FilterChip(
                    selected = sunlightPreference == value,
                    onClick = { sunlightPreference = value },
                    label = { Text(displayText) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pet friendly toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Безопасно для питомцев?", fontWeight = FontWeight.Medium)
            Switch(
                checked = petFriendly,
                onCheckedChange = { petFriendly = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Care level slider
        Text("Уровень ухода (1-5)", fontWeight = FontWeight.Medium)
        Slider(
            value = careLevel.toFloat(),
            onValueChange = { careLevel = it.toInt() },
            valueRange = 1f..5f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Уровень: $careLevel")

        Spacer(modifier = Modifier.height(16.dp))

        // Preferred location input
        OutlinedTextField(
            value = preferredLocation,
            onValueChange = { preferredLocation = it },
            label = { Text("Предпочтительное место (необязательно)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    val request = QuestionnaireRequest(
                        sunlightPreference = sunlightPreference,
                        petFriendly = petFriendly,
                        careLevel = careLevel,
                        preferredLocation = preferredLocation.ifBlank { null },
                        additionalPreferences = null
                    )
                    viewModel.submitQuestionnaire(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Получить рекомендации", fontSize = 18.sp)
            }
        }

        if (uiState.error != null) {
            Text(
                text = uiState.error ?: "",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}