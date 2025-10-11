package com.orizzonter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.mapbox.common.MapboxOptions
import com.orizzonter.app.core.designsystem.AppTheme
import com.orizzonter.app.core.designsystem.LocalAppTheme
import com.orizzonter.app.core.designsystem.OrizzonterTheme
import com.orizzonter.app.core.navigation.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala la pantalla splash (si aplica)
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // ✅ Configura el access token de Mapbox
        MapboxOptions.accessToken = getString(R.string.mapbox_access_token)

        // Permite que el contenido se dibuje detrás de las barras del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDarkTheme) }

            // Cambia color de íconos de sistema según el tema
            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView)
                    ?.isAppearanceLightStatusBars = !isDarkTheme
            }

            OrizzonterTheme(darkTheme = isDarkTheme) {
                CompositionLocalProvider(
                    LocalAppTheme provides AppTheme(
                        isDark = isDarkTheme,
                        toggleTheme = {
                            isDarkTheme = !isDarkTheme
                        }
                    )
                ) {
                    // 🔁 Navegación de la app
                    AppNavGraph(context = this)
                }
            }
        }
    }
}
