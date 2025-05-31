package com.example.planter.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planter.data.api.PlanterApi
import com.example.planter.data.api.request.ChatRequest
import com.example.planter.data.model.ChatMessage
import com.example.planter.data.model.ChatSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: PlanterApi
) : ViewModel() {
    private val _sessions = MutableStateFlow<List<ChatSession>>(emptyList())
    val sessions: StateFlow<List<ChatSession>> = _sessions

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private var currentSessionId: String? = null

    init {
        loadSessions()
    }

    private fun loadSessions() {
        viewModelScope.launch {
            try {
                _sessions.value = api.getChatSessions()
                currentSessionId = _sessions.value.firstOrNull()?.id
                currentSessionId?.let { loadMessages(it) }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadMessages(sessionId: String) {
        viewModelScope.launch {
            try {
                _messages.value = api.getChatMessages(sessionId)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun sendMessage(message: String) {
        currentSessionId?.let { sessionId ->
            viewModelScope.launch {
                try {
                    val response = api.sendChatMessage(
                        sessionId,
                        ChatRequest(message)
                    )
                    _messages.value = _messages.value + listOf(response.message)
                } catch (e: Exception) {
                    // Handle error
                }
            }
        } ?: createNewSession(message)
    }

    private fun createNewSession(message: String) {
        viewModelScope.launch {
            try {
                val session = api.createChatSession()
                currentSessionId = session.id
                sendMessage(message)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}