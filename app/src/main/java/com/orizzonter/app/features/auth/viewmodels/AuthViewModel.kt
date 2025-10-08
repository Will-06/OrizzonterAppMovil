package com.orizzonter.app.features.auth.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orizzonter.app.core.models.LoginRequest
import com.orizzonter.app.core.models.RegisterRequest
import com.orizzonter.app.core.network.ApiClient
import com.orizzonter.app.core.security.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val context: Context
) : ViewModel() {

    private val authManager = AuthManager(context)

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    fun login(email: String, password: String) {
        _loginState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.login(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!
                    authManager.login(
                        loginResponse.access_token,
                        loginResponse.user.email,
                        loginResponse.user.name
                    )
                    _loginState.value = AuthState.Success
                } else {
                    val errorMessage = when (response.code()) {
                        401 -> "Credenciales incorrectas"
                        422 -> "Error de validación"
                        500 -> "Error del servidor"
                        else -> "Error desconocido: ${response.errorBody()?.string()}"
                    }
                    _loginState.value = AuthState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        _registerState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.register(
                    RegisterRequest(name, email, password, confirmPassword)
                )
                if (response.isSuccessful && response.body() != null) {
                    val registerResponse = response.body()!!
                    authManager.login(
                        registerResponse.access_token,
                        registerResponse.user.email,
                        registerResponse.user.name
                    )
                    _registerState.value = AuthState.Success
                } else {
                    val errorMessage = when (response.code()) {
                        422 -> "Error en los datos de registro"
                        500 -> "Error del servidor"
                        else -> "Error en el registro: ${response.errorBody()?.string()}"
                    }
                    _registerState.value = AuthState.Error(errorMessage)
                }
            } catch (e: Exception) {
                _registerState.value = AuthState.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                ApiClient.apiService.logout()
            } catch (e: Exception) {
                // Ignorar errores de conexión en logout
            } finally {
                authManager.logout()
            }
        }
    }

    fun getAuthState() = authManager.authState
    fun getUserEmail() = authManager.getUserEmail()
    fun getUserName() = authManager.getUserName()

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
    }
}