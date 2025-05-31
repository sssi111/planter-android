package com.example.planter.data.repository.impl

import com.example.planter.data.api.request.LocationRequest
import com.example.planter.data.api.PlanterApi
import com.example.planter.data.model.Plant
import com.example.planter.data.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val api: PlanterApi
) : PlantRepository {

    override suspend fun getAllPlants(): Flow<List<Plant>> = flow {
        val plants = api.getAllPlants()
        emit(plants)
    }

    override suspend fun searchPlants(query: String): Flow<List<Plant>> = flow {
        val plants = api.searchPlants(query)
        emit(plants)
    }

    override suspend fun getPlantById(id: String): Flow<Plant?> = flow {
        val plant = api.getPlant(id)
        emit(plant)
    }

    override suspend fun toggleFavorite(plantId: String) {
        // First, get the plant to check if it's already a favorite
        val plant = api.getPlant(plantId)
        if (plant.isFavorite) {
            api.removeFromFavorites(plantId)
        } else {
            api.addToFavorites(plantId)
        }
    }
    
    override suspend fun getFavoritePlants(): Flow<List<Plant>> = flow {
        val plants = api.getFavoritePlants()
        emit(plants)
    }
    
    override suspend fun markAsWatered(plantId: String): Flow<Plant> = flow {
        val plant = api.markAsWatered(plantId)
        emit(plant)
    }
    
    override suspend fun getUserPlants(): Flow<List<Plant>> = flow {
        val plants = api.getUserPlants()
        emit(plants)
    }
    
    override suspend fun addUserPlant(plantId: String, location: String): Flow<Boolean> = flow {
        val response = api.addUserPlant(plantId, LocationRequest(location))
        emit(true)
    }
    
    override suspend fun updateUserPlant(plantId: String, location: String): Flow<Boolean> = flow {
        val response = api.updateUserPlant(plantId, LocationRequest(location))
        emit(true)
    }
    
    override suspend fun removeUserPlant(plantId: String): Flow<Boolean> = flow {
        val response = api.removeUserPlant(plantId)
        emit(true)
    }
}