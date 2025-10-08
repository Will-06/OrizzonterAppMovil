package com.orizzonter.app.core.security

import android.content.Context
import com.orizzonter.app.core.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthManager(
    private val context: Context
) {
    private val tokenManager = TokenManager(context)

    private val _authState = MutableStateFlow<AuthState>(
        if (tokenManager.isLoggedIn()) AuthState.Authenticated else AuthState.Unauthenticated
    )
    val authState: StateFlow<AuthState> = _authState

    init {
        tokenManager.getToken()?.let { token ->
            ApiClient.setAuthToken(token)
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(token: String, userEmail: String, userName: String) {
        tokenManager.saveAuthData(token, userEmail, userName)
        ApiClient.setAuthToken(token)
        _authState.value = AuthState.Authenticated
    }

    fun logout() {
        tokenManager.clearAuthData()
        ApiClient.clearAuthToken()
        _authState.value = AuthState.Unauthenticated
    }

    fun getUserEmail(): String? = tokenManager.getUserEmail()
    fun getUserName(): String? = tokenManager.getUserName()

    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
    }
}