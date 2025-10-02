package com.orizzonter.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.auth.*
import com.orizzonter.app.features.home.HomeScreen
import com.orizzonter.app.features.onboarding.OnboardingScreen
import com.orizzonter.app.features.splash.SplashScreen

@Composable
fun AppNavGraph() {
    // Controlador de navegación para gestionar la navegación entre pantallas
    val navController = rememberNavController()

    // Declaración del grafo de navegación con la pantalla de inicio como "splash"
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // Pantalla de presentación
        composable("splash") { SplashScreen(navController) }

        // Pantalla de bienvenida o introducción (onboarding)
        composable("onboarding") { OnboardingScreen(navController) }

        // Pantalla de inicio de sesión
        composable("login") { LoginScreen(navController) }

        // Pantalla principal del usuario
        composable("home") { HomeScreen() }

        // Pantalla para recuperar contraseña
        composable("forgot_password") { ForgotPasswordScreen(navController) }

        // Pantalla para crear una nueva cuenta
        composable("create_account") { CreateAccountScreen(navController) }
    }
}
