package com.orizzonter.app.core.designsystem

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Tipografía por defecto del sistema
val Typography = Typography()

// Clase que representa el estado del tema (oscuro o claro) y una función para cambiarlo
data class AppTheme(
    val isDark: Boolean,
    val toggleTheme: () -> Unit
)

// Proveedor local para acceder al estado del tema en toda la app
val LocalAppTheme = staticCompositionLocalOf<AppTheme> {
    error("No AppTheme provided") // Error si no se ha definido un AppTheme
}

// Paleta de colores para el tema claro
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6EA8E0),
    onPrimary = Color(0xFF1B1B1B),
    secondary = Color(0xFF9CC4E4),
    onSecondary = Color(0xFF1B1B1B),
    tertiary = Color(0xFFC6DFF6),
    onTertiary = Color(0xFF1B1B1B),
    background = Color(0xFFF0F6FA),
    onBackground = Color(0xFF1B1B1B),
    surface = Color(0xFFE6F0FA),
    onSurface = Color(0xFF1B1B1B),
    primaryContainer = Color(0xFFCCE3FA),
    onPrimaryContainer = Color(0xFF19508F),
    secondaryContainer = Color(0xFFD9E8FB),
    onSecondaryContainer = Color(0xFF19508F),
    error = Color(0xFFE57373)
)

// Paleta de colores para el tema oscuro
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8A8F94),
    onPrimary = Color(0xFFE0E0E0),
    secondary = Color(0xFFADB5BC),
    onSecondary = Color(0xFFE0E0E0),
    tertiary = Color(0xFFC4CDD2),
    onTertiary = Color(0xFF222222),
    background = Color(0xFF121619),
    onBackground = Color(0xFFE0E0E0),
    surface = Color(0xFF262B30),
    onSurface = Color(0xFFE0E0E0),
    primaryContainer = Color(0xFF3A4045),
    onPrimaryContainer = Color(0xFFE0E0E0),
    secondaryContainer = Color(0xFF4B5257),
    onSecondaryContainer = Color(0xFFE0E0E0),
    error = Color(0xFFFF8A80)
)

@Composable
fun OrizzonterTheme(
    darkTheme: Boolean, // Indica si se debe usar el tema oscuro
    content: @Composable () -> Unit // Contenido que se mostrará dentro del tema
) {
    // Selecciona el esquema de colores según el modo actual
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Aplica el tema de Material 3 con colores y tipografía definidos
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
