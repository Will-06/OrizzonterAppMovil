package com.orizzonter.app.core.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orizzonter.app.features.auth.*
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory
import com.orizzonter.app.features.home.HomeScreen
import com.orizzonter.app.features.onboarding.OnboardingScreen
import com.orizzonter.app.features.splash.SplashScreen

@Composable
fun AppNavGraph(context: Context) {
    val navController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )

    val startDestination = remember {
        if (authViewModel.getAuthState().value is com.orizzonter.app.core.security.AuthManager.AuthState.Authenticated) {
            AppDestination.Home.route
        } else {
            AppDestination.Splash.route
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestination.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(AppDestination.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }

        composable(AppDestination.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(AppDestination.CreateAccount.route) {
            CreateAccountScreen(navController = navController)
        }

        composable(AppDestination.ForgotPassword.route) {
            ForgotPasswordScreen(navController = navController)
        }

        composable(AppDestination.Home.route) {
            HomeScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(AppDestination.Login.route) {
                        popUpTo(AppDestination.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class AppDestination(val route: String) {
    object Splash : AppDestination("splash")
    object Onboarding : AppDestination("onboarding")
    object Login : AppDestination("login")
    object CreateAccount : AppDestination("create_account")
    object ForgotPassword : AppDestination("forgot_password")
    object Home : AppDestination("home")
}