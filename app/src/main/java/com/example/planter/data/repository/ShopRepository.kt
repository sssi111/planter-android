package com.example.planter.data.repository

import com.example.planter.data.model.Plant
import com.example.planter.data.model.Shop
import kotlinx.coroutines.flow.Flow

interface ShopRepository {
    suspend fun getAllShops(): Flow<List<Shop>>
    suspend fun getShopById(shopId: String): Flow<Shop?>
    suspend fun getShopPlants(shopId: String): Flow<List<Plant>>
}