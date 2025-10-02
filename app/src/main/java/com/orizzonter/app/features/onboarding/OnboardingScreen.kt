package com.orizzonter.app.features.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orizzonter.app.R
import kotlinx.coroutines.launch

// Data class que representa una página del onboarding
data class OnboardingPage(
    val title: String,
    val description: String,
    val imageRes: Int,
)

@Composable
fun OnboardingScreen(navController: NavController) {
    // Lista de páginas con título, descripción e imagen
    val pages = listOf(
        OnboardingPage(
            "Bienvenido",
            "Empieza tu aventura sobre dos ruedas con Orizzonter, la app pensada para los verdaderos amantes del ciclismo. Descubre rutas, conoce nuevos destinos y transforma cada pedaleo en una experiencia única.",
            R.drawable.bienvenido2
        ),
        OnboardingPage(
            "Rutas",
            "Pedalea por caminos increíbles que te esperan a la vuelta de cada curva. Con Orizzonter, tendrás acceso a una amplia variedad de rutas adaptadas a tu nivel, intereses y ganas de explorar.",
            R.drawable.rutas3
        ),
        OnboardingPage(
            "Comunidad",
            "Súmate a una comunidad vibrante que comparte tu pasión por el ciclismo. Intercambia experiencias, únete a retos, encuentra compañeros de ruta y crece junto a otros ciclistas como tú.",
            R.drawable.comunidad2
        )
    )

    // Estado del paginador (horizontal)
    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Paginador horizontal que muestra el contenido de cada página
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPageContent(page = pages[page])
            }

            // Indicador de página (dots)
            PageIndicator(pagerState)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón siguiente o empezar, dependiendo de la página actual
            NextButton(
                isLastPage = pagerState.currentPage == pages.lastIndex,
                onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < pages.lastIndex) {
                            // Si no es la última página, navegar a la siguiente con animación
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            // Si es la última, navegar a la pantalla de login
                            navController.navigate("login")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Caja que contiene la imagen con fondo y bordes redondeados
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 100.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(page.imageRes),
                contentDescription = page.title,
                modifier = Modifier.size(480.dp)
            )
        }

        // Texto descriptivo debajo de la imagen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 24.dp)
                .padding(top = 72.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = page.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                lineHeight = 28.sp,
                letterSpacing = 0.25.sp,
                modifier = Modifier.widthIn(max = 420.dp)
            )
        }
    }
}

@Composable
fun PageIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Repetir por cada página, creando un dot que cambia tamaño y color según selección
        repeat(pagerState.pageCount) { index ->
            val isSelected = index == pagerState.currentPage

            // Animaciones para tamaño, color y sombra
            val size by animateDpAsState(if (isSelected) 14.dp else 10.dp)
            val color by animateColorAsState(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
            val shadowElevation by animateDpAsState(if (isSelected) 6.dp else 0.dp)

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(size)
                    .shadow(shadowElevation, CircleShape)
                    .background(color, CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Composable
fun NextButton(
    isLastPage: Boolean,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animaciones para tamaño, color de fondo y borde cuando el botón está presionado o no
    val size by animateDpAsState(if (isPressed) 52.dp else 56.dp)
    val backgroundColor by animateColorAsState(
        if (isPressed)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else
            MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
    )
    val borderColor by animateColorAsState(
        if (isPressed)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        else
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 90.dp , vertical = 5.dp)
            .height(size)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                        onClick()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLastPage) "¡Comencemos la aventura!" else "Siguiente",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.5.sp,
            fontFamily = FontFamily.Default
        )

    }
}
