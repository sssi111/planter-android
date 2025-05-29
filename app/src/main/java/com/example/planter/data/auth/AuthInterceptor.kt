package com.example.planter.data.auth

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authManager: AuthManager
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Если токен отсутствует, отправляем запрос без изменений
        val token = authManager.getToken() ?: return chain.proceed(originalRequest)
        
        // Добавляем токен в заголовок Authorization
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        
        return chain.proceed(newRequest)
    }
}