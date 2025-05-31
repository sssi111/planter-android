package com.example.planter.ui.screens.recommendations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.planter.R
import com.example.planter.data.model.PlantQuestionnaire
import com.example.planter.navigation.Screen

@Composable
fun QuestionnaireScreen(
    navController: NavController,
    onQuestionnaireComplete: (PlantQuestionnaire) -> Unit
) {
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
            text = "Plant Preferences",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Sunlight preference selection
        Text("Sunlight Preference", fontWeight = FontWeight.Medium)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            listOf("Low", "Medium", "High").forEach { level ->
                FilterChip(
                    selected = sunlightPreference == level,
                    onClick = { sunlightPreference = level },
                    label = { Text(level) },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pet friendly toggle
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Pet Friendly?", fontWeight = FontWeight.Medium)
            Switch(
                checked = petFriendly,
                onCheckedChange = { petFriendly = it },
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Care level slider
        Text("Care Level (1-5)", fontWeight = FontWeight.Medium)
        Slider(
            value = careLevel.toFloat(),
            onValueChange = { careLevel = it.toInt() },
            valueRange = 1f..5f,
            steps = 4,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Level: $careLevel")

        Spacer(modifier = Modifier.height(16.dp))

        // Preferred location input
        OutlinedTextField(
            value = preferredLocation,
            onValueChange = { preferredLocation = it },
            label = { Text("Preferred Location (optional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val questionnaire = PlantQuestionnaire(
                    id = "",
                    userId = null,
                    sunlightPreference = sunlightPreference,
                    petFriendly = petFriendly,
                    careLevel = careLevel,
                    preferredLocation = preferredLocation.ifBlank { null },
                    additionalPreferences = null,
                    createdAt = ""
                )
                onQuestionnaireComplete(questionnaire)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Get Recommendations", fontSize = 18.sp)
        }
    }
}