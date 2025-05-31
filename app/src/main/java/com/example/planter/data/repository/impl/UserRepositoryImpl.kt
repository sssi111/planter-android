package com.example.planter.data.repository.impl

import com.example.planter.data.api.PlanterApi
import com.example.planter.data.api.request.LoginRequest
import com.example.planter.data.api.request.RegisterRequest
import com.example.planter.data.auth.AuthManager
import com.example.planter.data.model.User
import com.example.planter.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: PlanterApi,
    private val authManager: AuthManager
) : UserRepository {

    private var currentUser: User? = null

    override suspend fun getCurrentUser(): Flow<User?> = flow {
        emit(currentUser)
    }

    override suspend fun updateUser(user: User) {
        currentUser = api.updateUser(user.id, user)
    }

    override suspend fun login(email: String, password: String): Flow<User> = flow {
        val response = api.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let { authResponse ->
                authManager.saveToken(authResponse.token)
                currentUser = authResponse.user
                emit(authResponse.user)
            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Login failed: ${response.code()}")
        }
    }

    override suspend fun register(email: String, password: String, name: String): Flow<User> = flow {
        val response = api.register(RegisterRequest(name, email, password))
        if (response.isSuccessful) {
            response.body()?.let { authResponse ->
                authManager.saveToken(authResponse.token)
                currentUser = authResponse.user
                emit(authResponse.user)
            } ?: throw Exception("Empty response body")
        } else {
            throw Exception("Registration failed: ${response.code()}")
        }
    }

    override suspend fun logout() {
        authManager.clearToken()
        currentUser = null
    }
}