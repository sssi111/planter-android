package com.example.planter.data.repository

import com.example.planter.data.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    suspend fun getAllPlants(): Flow<List<Plant>>
    suspend fun searchPlants(query: String): Flow<List<Plant>>
    suspend fun getPlantById(id: String): Flow<Plant?>
    suspend fun toggleFavorite(plantId: String)
    suspend fun getFavoritePlants(): Flow<List<Plant>>
    suspend fun markAsWatered(plantId: String): Flow<Plant>
    suspend fun getUserPlants(): Flow<List<Plant>>
    suspend fun addUserPlant(plantId: String, location: String): Flow<Boolean>
    suspend fun updateUserPlant(plantId: String, location: String): Flow<Boolean>
    suspend fun removeUserPlant(plantId: String): Flow<Boolean>
}