package com.example.planter.data.repository.impl

import com.example.planter.data.api.PlanterApi
import com.example.planter.data.model.Plant
import com.example.planter.data.model.Shop
import com.example.planter.data.repository.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val api: PlanterApi
) : ShopRepository {

    override suspend fun getAllShops(): Flow<List<Shop>> = flow {
        val shops = api.getShops()
        emit(shops)
    }

    override suspend fun getShopById(shopId: String): Flow<Shop?> = flow {
        val shop = api.getShop(shopId)
        emit(shop)
    }

    override suspend fun getShopPlants(shopId: String): Flow<List<Plant>> = flow {
        val plants = api.getShopPlants(shopId)
        emit(plants)
    }
}