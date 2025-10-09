package com.orizzonter.app.features.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory
import com.orizzonter.app.features.home.components.BottomBar
import com.orizzonter.app.features.home.screens.community.CommunityScreen
import com.orizzonter.app.features.home.screens.routes.RoutesScreen
import com.orizzonter.app.features.home.screens.services.ServiceDetailScreen
import com.orizzonter.app.features.home.screens.services.ServicesScreen
import com.orizzonter.app.features.home.screens.settings.SettingsScreen

@Composable
fun HomeScreen(
    onLogout: () -> Unit = {}
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )

    // Bloquear el botón de retroceso físico
    BackHandler {
        // No hacer nada - bloquear el retroceso
        // O puedes mostrar un diálogo de confirmación para salir
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.Routes.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(HomeDestination.Routes.route) {
                RoutesScreen()
            }
            composable("services") {
                ServicesScreen(navController)
            }
            composable(HomeDestination.Social.route) {
                CommunityScreen()
            }
            composable(HomeDestination.Settings.route) {
                SettingsScreen(
                    onLogout = onLogout,
                    authViewModel = authViewModel
                )

            }
            // Ruta de detalles del servicio
            composable("serviceDetail/{title}/{description}") { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                ServiceDetailScreen(title, description, navController)
            }
        }
    }
}

sealed class HomeDestination(val route: String) {
    object Routes : HomeDestination("routes")
    object Services : HomeDestination("services")
    object Social : HomeDestination("social")
    object Settings : HomeDestination("settings")
}