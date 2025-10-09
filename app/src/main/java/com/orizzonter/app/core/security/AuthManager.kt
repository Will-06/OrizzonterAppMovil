package com.orizzonter.app.core.security

import android.content.Context
import android.graphics.Bitmap
import com.orizzonter.app.core.network.ApiClient
import com.orizzonter.app.core.storage.AvatarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthManager(
    private val context: Context
) {
    private val tokenManager = TokenManager(context)
    private val avatarManager = AvatarManager(context)

    private val _authState = MutableStateFlow<AuthState>(
        if (tokenManager.isLoggedIn()) AuthState.Authenticated else AuthState.Unauthenticated
    )
    val authState: StateFlow<AuthState> = _authState

    private val _currentAvatarPath = MutableStateFlow<String?>(null)
    val currentAvatarPath: StateFlow<String?> = _currentAvatarPath

    init {
        tokenManager.getToken()?.let { token ->
            ApiClient.setAuthToken(token)
            _authState.value = AuthState.Authenticated

            // Cargar avatar al iniciar si existe
            tokenManager.getUserEmail()?.let { email ->
                loadUserAvatar(email)
            }
        }
    }

    fun login(token: String, userEmail: String, userName: String) {
        tokenManager.saveAuthData(token, userEmail, userName)
        ApiClient.setAuthToken(token)
        _authState.value = AuthState.Authenticated

        // Cargar avatar del usuario que acaba de loguearse
        loadUserAvatar(userEmail)
    }

    fun logout() {
        // Eliminar avatar local al hacer logout
        tokenManager.getUserEmail()?.let { email ->
            avatarManager.deleteAvatar(email)
        }

        tokenManager.clearAuthData()
        ApiClient.clearAuthToken()
        _currentAvatarPath.value = null
        _authState.value = AuthState.Unauthenticated
    }

    // En el método saveAvatar del AuthManager, asegúrate de que se actualice el estado:
    fun saveAvatar(bitmap: Bitmap): Boolean {
        val userEmail = getUserEmail() ?: return false
        return avatarManager.saveAvatar(bitmap, userEmail).also { success ->
            if (success) {
                loadUserAvatar(userEmail)
                // Notificar que el avatar cambió
                _currentAvatarPath.value = avatarManager.getAvatarPath(userEmail)
            }
        }
    }

    private fun loadUserAvatar(userEmail: String) {
        val avatarPath = avatarManager.getAvatarPath(userEmail)
        _currentAvatarPath.value = avatarPath
    }

    fun getCurrentAvatarPath(): String? = _currentAvatarPath.value

    fun getUserEmail(): String? = tokenManager.getUserEmail()
    fun getUserName(): String? = tokenManager.getUserName()

    sealed class AuthState {
        object Authenticated : AuthState()
        object Unauthenticated : AuthState()
    }
}