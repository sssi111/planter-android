package com.example.planter.data.repository

import com.example.planter.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getCurrentUser(): Flow<User?>
    suspend fun updateUser(user: User)
    suspend fun login(email: String, password: String): Flow<User>
    suspend fun register(email: String, password: String, name: String): Flow<User>
    suspend fun logout()
} 