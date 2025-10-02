package com.orizzonter.app.features.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.home.components.BottomBar
import com.orizzonter.app.features.home.screens.community.CommunityScreen
import com.orizzonter.app.features.home.screens.routes.RoutesScreen
import com.orizzonter.app.features.home.screens.services.ServicesScreen
import com.orizzonter.app.features.home.screens.settings.SettingsScreen

@Composable
fun HomeScreen() {
    // Controlador de navegación para manejar la navegación interna de pestañas
    val navController = rememberNavController()

    Scaffold(
        // Barra inferior que recibe el navController para controlar la navegación
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        // Contenedor de navegación interna con rutas definidas
        NavHost(
            navController = navController,
            startDestination = "routes", // Pantalla inicial
            modifier = Modifier.padding(padding) // Ajusta el padding según Scaffold
        ) {
            // Cada "composable" es una pantalla vinculada a una ruta específica
            composable("routes") { RoutesScreen() }
            composable("services") { ServicesScreen() }
            composable("social") { CommunityScreen() }
            composable("settings") { SettingsScreen() }
        }
    }
}
