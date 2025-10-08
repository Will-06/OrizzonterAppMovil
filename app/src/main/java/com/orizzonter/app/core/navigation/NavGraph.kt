package com.orizzonter.app.core.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.core.security.AuthManager
import com.orizzonter.app.features.auth.*
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory
import com.orizzonter.app.features.home.HomeScreen
import com.orizzonter.app.features.home.screens.settings.SettingsScreen
import com.orizzonter.app.features.onboarding.OnboardingScreen
import com.orizzonter.app.features.splash.SplashScreen

@Composable
fun AppNavGraph(context: Context) {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )

    val startDestination = remember {
        if (authViewModel.getAuthState().value is AuthManager.AuthState.Authenticated) {
            "home"
        } else {
            "splash"
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash screen
        composable("splash") {
            SplashScreen(navController)
        }

        // Onboarding
        composable("onboarding") {
            OnboardingScreen(navController)
        }

        // Login screen
        composable("login") {
            LoginScreen(
                navController = navController
            )
        }

        // Create account screen
        composable("create_account") {
            CreateAccountScreen(
                navController = navController
            )
        }

        // Forgot password screen
        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        // Home screen
        composable("home") {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        // Settings screen (añadido)
        composable("settings") {
            SettingsScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                authViewModel = authViewModel
            )
        }
    }
}