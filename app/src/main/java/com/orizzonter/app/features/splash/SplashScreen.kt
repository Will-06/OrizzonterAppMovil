package com.orizzonter.app.features.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import com.orizzonter.app.R

@Composable
fun SplashScreen(navController: NavController) {
    // Animatable para la posición horizontal del logo (empieza fuera de pantalla a la izquierda)
    val logoOffsetX = remember { Animatable(-400f) }
    // Animatable para la opacidad del texto (empieza invisible)
    val contentAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animar el logo a la posición 0 (centrado) con easing suave
        logoOffsetX.animateTo(0f, tween(1000, easing = EaseOutCubic))
        // Animar la opacidad del texto a visible (1f)
        contentAlpha.animateTo(1f, tween(1000))
        // Esperar 2.5 segundos antes de navegar a la siguiente pantalla
        delay(2500)
        // Navegar a la pantalla de onboarding y eliminar la pantalla splash del backstack
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Contenedor principal centrado con fondo del tema
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo con animación de desplazamiento horizontal
            Image(
                painter = painterResource(R.drawable.logo_orizzonter),
                contentDescription = "Logo principal",
                modifier = Modifier
                    .size(150.dp)
                    .offset { IntOffset(logoOffsetX.value.toInt(), 0) }
            )
            Spacer(Modifier.height(16.dp))
            // Texto con animación de opacidad
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.alpha(contentAlpha.value)
            ) {
                Text(
                    "Orizzonter",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    "Explora nuevos horizontes",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
