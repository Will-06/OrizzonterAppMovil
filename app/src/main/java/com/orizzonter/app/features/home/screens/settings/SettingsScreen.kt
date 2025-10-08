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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orizzonter.app.R
import com.orizzonter.app.core.designsystem.LocalAppTheme
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory

@Composable
fun SettingsScreen(
    onLogout: () -> Unit = {},
    authViewModel: AuthViewModel? = null
) {
    val theme = LocalAppTheme.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val viewModel = authViewModel ?: viewModel(
        factory = AuthViewModelFactory(context)
    )

    val userName by remember {
        derivedStateOf { viewModel.getUserName() ?: "Usuario" }
    }
    val userEmail by remember {
        derivedStateOf { viewModel.getUserEmail() ?: "email@ejemplo.com" }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        ProfileSection(
            userName = userName,
            userEmail = userEmail
        )

        MainSettingsSection(theme = theme)

        RoutePreferencesSection()

        CommunitySection()

        SecurityAlertsSection()

        MapSection()

        PrivacySection()

        LogoutButton(
            onLogout = {
                viewModel.logout()
                onLogout()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally) // ✅ FIX AQUÍ
        )

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun ProfileSection(
    userName: String,
    userEmail: String
) {
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
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MainSettingsSection(theme: com.orizzonter.app.core.designsystem.AppTheme) {
    SettingToggle(
        title = "Modo oscuro",
        checked = theme.isDark,
        onCheckedChange = { theme.toggleTheme() }
    )
    SettingToggle(
        title = "Notificaciones",
        checked = true
    )
    SettingSelector(
        title = "Idioma",
        selected = "Español"
    )
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
private fun RoutePreferencesSection() {
    Text(
        text = "Preferencias de Ruta",
        style = MaterialTheme.typography.titleSmall
    )
    SettingSelector(
        title = "Tipo de ciclismo",
        selected = "Montaña"
    )
    SettingSelector(
        title = "Nivel de dificultad",
        selected = "Moderado"
    )
}

@Composable
private fun CommunitySection() {
    Text(
        text = "Comunidad",
        style = MaterialTheme.typography.titleSmall
    )
    SettingToggle(
        title = "Mostrar mi perfil en rutas públicas",
        checked = true
    )
    SettingToggle(
        title = "Permitir que otros me sigan",
        checked = true
    )
}

@Composable
private fun SecurityAlertsSection() {
    Text(
        text = "Alertas de Seguridad",
        style = MaterialTheme.typography.titleSmall
    )
    SettingToggle(
        title = "Notificar sobre rutas peligrosas",
        checked = true
    )
}

@Composable
private fun MapSection() {
    Text(
        text = "Mapa",
        style = MaterialTheme.typography.titleSmall
    )
    SettingToggle(
        title = "Mostrar talleres automáticamente",
        checked = true
    )
}

@Composable
private fun PrivacySection() {
    Text(
        text = "Privacidad",
        style = MaterialTheme.typography.titleSmall
    )
    SettingToggle(
        title = "Guardar historial de rutas",
        checked = true
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
private fun LogoutButton(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier // ✅ Se recibe el modifier
) {
    Button(
        onClick = onLogout,
        modifier = modifier
            .fillMaxWidth(0.6f)
            .height(48.dp)
            .border(
                width = 1.dp,
                brush = SolidColor(MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f),
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Text(
            text = "Cerrar sesión",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SettingToggle(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground
        )
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
fun SettingSelector(
    title: String,
    selected: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = title)
            Text(
                text = selected,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Seleccionar opción"
        )
    }
}
