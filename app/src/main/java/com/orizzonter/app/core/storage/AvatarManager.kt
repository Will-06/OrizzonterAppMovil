package com.orizzonter.app.core.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AvatarManager(private val context: Context) {

    companion object {
        private const val AVATAR_DIR = "avatars"
    }

    fun saveAvatar(bitmap: Bitmap, userEmail: String): Boolean {
        return try {
            val avatarDir = File(context.filesDir, AVATAR_DIR)
            if (!avatarDir.exists()) {
                avatarDir.mkdirs()
            }

            // Nombre único basado en el email del usuario
            val avatarFile = File(avatarDir, "${userEmail.hashCode()}.jpg")

            FileOutputStream(avatarFile).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun getAvatarPath(userEmail: String): String? {
        return try {
            val avatarFile = File(
                File(context.filesDir, AVATAR_DIR),
                "${userEmail.hashCode()}.jpg"
            )

            if (avatarFile.exists()) {
                avatarFile.absolutePath
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteAvatar(userEmail: String): Boolean {
        return try {
            val avatarFile = File(
                File(context.filesDir, AVATAR_DIR),
                "${userEmail.hashCode()}.jpg"
            )

            if (avatarFile.exists()) {
                avatarFile.delete()
            } else {
                true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun hasAvatar(userEmail: String): Boolean {
        val avatarFile = File(
            File(context.filesDir, AVATAR_DIR),
            "${userEmail.hashCode()}.jpg"
        )
        return avatarFile.exists()
    }
}