package com.orizzonter.app.features.home.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orizzonter.app.R
import com.orizzonter.app.core.designsystem.LocalAppTheme

@Composable
fun SettingsScreen(onLogout: () -> Unit = {}) {
    val theme = LocalAppTheme.current
    val scrollState = rememberScrollState()

    // Contenedor principal con scroll vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(24.dp))

        // Sección de perfil centrado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.fotoperfil),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape) // Imagen circular
            )
            Spacer(Modifier.height(8.dp))
            Text("Orizzonter", style = MaterialTheme.typography.bodyLarge)
            Text(
                "orizzonter@gmail.com",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Opciones principales de configuración con toggles y selectores
        SettingToggle("Modo oscuro", theme.isDark) { theme.toggleTheme() }
        SettingToggle("Notificaciones", checked = true)
        SettingSelector("Idioma", selected = "Español")

        Spacer(Modifier.height(32.dp))

        // Preferencias específicas de ruta
        Text("Preferencias de Ruta", style = MaterialTheme.typography.titleSmall)
        SettingSelector("Tipo de ciclismo", selected = "Montaña")
        SettingSelector("Nivel de dificultad", selected = "Moderado")

        // Opciones de comunidad
        Text("Comunidad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Mostrar mi perfil en rutas públicas", checked = true)
        SettingToggle("Permitir que otros me sigan", checked = true)

        // Alertas de seguridad
        Text("Alertas de Seguridad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Notificar sobre rutas peligrosas", checked = true)

        // Opciones de mapa
        Text("Mapa", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Mostrar talleres automáticamente", checked = true)

        // Privacidad
        Text("Privacidad", style = MaterialTheme.typography.titleSmall)
        SettingToggle("Guardar historial de rutas", checked = true)

        Spacer(Modifier.height(10.dp))

        // Botón para cerrar sesión
        Button(
            onClick = onLogout,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.6f)
                .height(48.dp)
                .border(
                    1.dp,
                    SolidColor(MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                    RoundedCornerShape(24.dp)
                ),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Cerrar sesión", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(10.dp))
    }
}

@Composable
fun SettingToggle(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    // Fila con título y switch para activar/desactivar opción
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = MaterialTheme.colorScheme.onBackground)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        )
    }
}

@Composable
fun SettingSelector(title: String, selected: String) {
    // Fila con título y opción seleccionada con ícono desplegable
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title)
            Text(
                selected,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
    }
}
