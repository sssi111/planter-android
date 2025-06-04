package com.example.planter.ui.screens.plant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.planter.R
import com.example.planter.data.model.CareInstructions
import com.example.planter.data.model.HumidityLevel
import com.example.planter.data.model.SunlightLevel
import com.example.planter.data.model.TemperatureRange
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.compose.ui.graphics.Color

@Composable
fun PlantDetailsScreen(
    plantId: String,
    onBackClick: () -> Unit,
    viewModel: PlantDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val plant = uiState.plant
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
    ) {
        Box {
            // Plant image
            if (plant != null) {
                AsyncImage(
                    model = plant.imageUrl,
                    contentDescription = plant.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }

            // Back button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            // Favorite button
            if (plant != null) {
                IconButton(
                    onClick = { viewModel.toggleFavorite() },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd)
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(24.dp)
                        )
                ) {
                    Icon(
                        imageVector = if (plant.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (plant.isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (plant.isFavorite) Color(0xFFE91E63) else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        } else if (plant != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Plant name and scientific name
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = plant.scientificName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Description
                Text(
                    text = "Описание",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = plant.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // Watering information
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                if (plant.lastWatered != null) {
                                    Text(
                                        text = "Последний полив: ${formatDate(plant.lastWatered)}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                                
                                if (plant.nextWatering != null) {
                                    Text(
                                        text = "Следующий полив: ${formatDate(plant.nextWatering)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            
                            Button(
                                onClick = { viewModel.markAsWatered() },
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Полить")
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Care instructions
                Text(
                    text = "Инструкции по уходу",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                CareInstructionsSection(plant.careInstructions)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Location information
                if (plant.location != null) {
                    Text(
                        text = "Расположение: ${plant.location}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                // Shop information
                if (plant.shopId != null && plant.price != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Доступно к покупке: ${plant.price}₽",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun CareInstructionsSection(careInstructions: CareInstructions) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CareInstructionItem(
                title = "Полив",
                description = "Каждые ${careInstructions.wateringFrequency} дней"
            )
            
            CareInstructionItem(
                title = "Освещение",
                description = getSunlightDescription(careInstructions.sunlight)
            )
            
            CareInstructionItem(
                title = "Температура",
                description = "${careInstructions.temperature.min}°C - ${careInstructions.temperature.max}°C"
            )
            
            CareInstructionItem(
                title = "Влажность",
                description = getHumidityDescription(careInstructions.humidity)
            )
            
            CareInstructionItem(
                title = "Тип почвы",
                description = careInstructions.soilType
            )
            
            CareInstructionItem(
                title = "Удобрение",
                description = "Каждые ${careInstructions.fertilizerFrequency} дней"
            )
            
            if (careInstructions.additionalNotes.isNotBlank()) {
                CareInstructionItem(
                    title = "Дополнительные заметки",
                    description = careInstructions.additionalNotes
                )
            }
        }
    }
}

@Composable
fun CareInstructionItem(title: String, description: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

fun getSunlightDescription(level: SunlightLevel): String {
    return when (level) {
        SunlightLevel.LOW -> "Низкое освещение (тень или полутень)"
        SunlightLevel.MEDIUM -> "Среднее освещение (частичное солнце)"
        SunlightLevel.HIGH -> "Высокое освещение (прямой солнечный свет)"
    }
}

fun getHumidityDescription(level: HumidityLevel): String {
    return when (level) {
        HumidityLevel.LOW -> "Низкая влажность (30-40%)"
        HumidityLevel.MEDIUM -> "Средняя влажность (40-60%)"
        HumidityLevel.HIGH -> "Высокая влажность (60%+)"
    }
}

private fun formatDate(date: OffsetDateTime?): String {
    if (date == null) return "Н/Д"
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale("ru"))
    return date.format(formatter)
}