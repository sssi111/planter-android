package com.example.planter.data.api

import com.example.planter.data.model.*
import retrofit2.http.*

interface PlanterApi {
    // Auth endpoints
    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): AuthResponse
    
    @POST("auth/register")
    suspend fun register(@Body user: RegisterRequest): AuthResponse
    
    // User endpoints
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User
    
    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body user: User): User
    
    // Plant endpoints
    @GET("plants")
    suspend fun getAllPlants(): List<Plant>
    
    @GET("plants/{plantId}")
    suspend fun getPlant(@Path("plantId") plantId: String): Plant
    
    @GET("plants/search")
    suspend fun searchPlants(@Query("query") query: String): List<Plant>
    
    @GET("plants/favorites")
    suspend fun getFavoritePlants(): List<Plant>
    
    @POST("plants/{plantId}/favorite")
    suspend fun addToFavorites(@Path("plantId") plantId: String): MessageResponse
    
    @DELETE("plants/{plantId}/favorite")
    suspend fun removeFromFavorites(@Path("plantId") plantId: String): MessageResponse
    
    @POST("plants/{plantId}/water")
    suspend fun markAsWatered(@Path("plantId") plantId: String): Plant
    
    @GET("plants/user")
    suspend fun getUserPlants(): List<Plant>
    
    @POST("plants/user/{plantId}")
    suspend fun addUserPlant(@Path("plantId") plantId: String, @Body request: LocationRequest): MessageResponse
    
    @PUT("plants/user/{plantId}")
    suspend fun updateUserPlant(@Path("plantId") plantId: String, @Body request: LocationRequest): MessageResponse
    
    @DELETE("plants/user/{plantId}")
    suspend fun removeUserPlant(@Path("plantId") plantId: String): MessageResponse
    
    // Shop endpoints
    @GET("shops")
    suspend fun getShops(): List<Shop>
    
    @GET("shops/{shopId}")
    suspend fun getShop(@Path("shopId") shopId: String): Shop
    
    @GET("shops/{shopId}/plants")
    suspend fun getShopPlants(@Path("shopId") shopId: String): List<Plant>
    
    // Recommendations endpoints
    @POST("recommendations/questionnaire")
    suspend fun saveQuestionnaire(@Body request: QuestionnaireRequest): PlantQuestionnaire
    
    @GET("recommendations/questionnaire/{questionnaireId}")
    suspend fun getRecommendations(@Path("questionnaireId") questionnaireId: String): List<Plant>
}

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val user: User
)

data class MessageResponse(
    val message: String
)

data class LocationRequest(
    val location: String
)

data class ErrorResponse(
    val error: String
)