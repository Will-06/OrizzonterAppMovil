package com.orizzonter.app.features.profile.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orizzonter.app.core.security.AuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStream

class AvatarViewModel(private val context: Context) : ViewModel() {

    private val authManager = AuthManager(context)

    private val _currentAvatarPath = MutableStateFlow<String?>(authManager.getCurrentAvatarPath())
    val currentAvatarPath: StateFlow<String?> = _currentAvatarPath

    fun uploadAvatar(uri: Uri) {
        viewModelScope.launch {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (bitmap != null) {
                    val success = authManager.saveAvatar(bitmap)
                    if (success) {
                        // Actualizar el estado con la nueva ruta
                        _currentAvatarPath.value = authManager.getCurrentAvatarPath()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}