package com.orizzonter.app.features.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.orizzonter.app.R

// Define los ítems del menú inferior con ruta, título e ícono o recurso drawable
sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
    @DrawableRes val drawableRes: Int? = null
) {
    object Routes : BottomNavItem("routes", "Rutas", drawableRes = R.drawable.cursor)
    object Services : BottomNavItem("services", "Servicios", drawableRes = R.drawable.handshake)
    object Social : BottomNavItem("social", "Comunidad", drawableRes = R.drawable.community)
    object Settings : BottomNavItem("settings", "Ajustes", drawableRes = R.drawable.settings)
}

// Composable que renderiza la barra de navegación inferior
@Composable
fun BottomBar(navController: NavHostController) {
    // Lista de ítems que se mostrarán en la barra
    val items = listOf(
        BottomNavItem.Routes,
        BottomNavItem.Services,
        BottomNavItem.Social,
        BottomNavItem.Settings
    )

    // Obtiene la ruta actual para saber qué ítem está seleccionado
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Contenedor de la barra con estilo y borde redondeado
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                RoundedCornerShape(24.dp)
            ),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(24.dp)
    ) {
        // Barra de navegación propiamente dicha
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            // Por cada ítem crea un NavigationBarItem
            items.forEach { item ->
                // Determina si el ítem está seleccionado
                val selected = currentRoute == item.route
                // Color según estado seleccionado o no
                val tintColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

                NavigationBarItem(
                    icon = {
                        // Muestra el icono ya sea vector o drawable
                        when {
                            item.icon != null -> Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = tintColor
                            )
                            item.drawableRes != null -> Icon(
                                painter = painterResource(id = item.drawableRes),
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp),
                                tint = tintColor
                            )
                        }
                    },
                    label = { Text(text = item.title, color = tintColor) }, // Texto del ítem
                    selected = selected, // Estado seleccionado
                    onClick = {
                        // Navega a la ruta correspondiente
                        navController.navigate(item.route) {
                            // Evita múltiples instancias y mantiene el estado
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors( // Colores personalizados para estados
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
            }
        }
    }
}
