package com.example.planter.data.auth

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }
    
    fun getToken(): String? {
        return prefs.getString(KEY_TOKEN, null)
    }
    
    fun clearToken() {
        prefs.edit().remove(KEY_TOKEN).apply()
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    
    companion object {
        private const val PREFS_NAME = "planter_auth_prefs"
        private const val KEY_TOKEN = "auth_token"
    }
}