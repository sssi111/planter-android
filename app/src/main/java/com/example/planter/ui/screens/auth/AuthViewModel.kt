package com.example.planter.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.auth.AuthManager
import com.example.planter.data.model.User
import com.example.planter.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authManager: AuthManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(authManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        _isLoggedIn.value = authManager.isLoggedIn()
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            userRepository.login(email, password)
                .catch { e ->
                    _authState.value = AuthState.Error(e.message ?: "Unknown error")
                }
                .collect { user ->
                    _authState.value = AuthState.Success(user)
                    _isLoggedIn.value = true
                }
        }
    }

    fun register(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            userRepository.register(email, password, name)
                .catch { e ->
                    _authState.value = AuthState.Error(e.message ?: "Unknown error")
                }
                .collect { user ->
                    _authState.value = AuthState.Success(user)
                    _isLoggedIn.value = true
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _isLoggedIn.value = false
            _authState.value = AuthState.Idle
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}