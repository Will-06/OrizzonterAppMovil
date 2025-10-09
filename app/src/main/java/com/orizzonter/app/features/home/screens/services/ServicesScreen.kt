package com.orizzonter.app.features.home.screens.services

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ServicesScreen(navController: NavController) {
    val services = listOf(
        Triple("Taller de Bicis", "Repara y mantén tu bicicleta con los mejores expertos.", Icons.Default.Build),
        Triple("Rutas Guiadas", "Explora rutas seguras y emocionantes con acompañamiento profesional.", Icons.Default.Map),
        Triple("Alquiler de Bicicletas", "Encuentra bicicletas disponibles para cualquier tipo de aventura.", Icons.Default.DirectionsBike),
        Triple("Tiendas Especializadas", "Compra accesorios y equipo de calidad para tu ciclismo.", Icons.Default.Storefront),
        Triple("Consejos de Salud", "Información sobre nutrición, cuidado y bienestar para ciclistas.", Icons.Default.HealthAndSafety),
        Triple("Puntos de Hidratación", "Localiza estaciones para hidratarte durante tus rutas.", Icons.Default.LocalCafe),
        Triple("Cicloturismo", "Descubre experiencias turísticas en bicicleta por lugares emblemáticos.", Icons.Default.Explore),
        Triple("Taller Móvil", "Servicio de reparación que viene a tu ubicación cuando lo necesites.", Icons.Default.MiscellaneousServices),
        Triple("Eventos Ciclísticos", "Participa en competencias y reuniones para ciclistas.", Icons.Default.Event),
        Triple("Seguros para Bicis", "Protege tu bicicleta contra robos y daños accidentales.", Icons.Default.Security),
        Triple("Estacionamientos Seguros", "Lugares vigilados para dejar tu bicicleta con tranquilidad.", Icons.Default.Lock),
        Triple("Clínicas de Mantenimiento", "Talleres periódicos para mantener tu bici en óptimas condiciones.", Icons.Default.Handyman),
        Triple("Alquiler de Equipos", "Cascos, luces y otros accesorios disponibles para rentar.", Icons.Default.Devices),
        Triple("Rutas Nocturnas", "Recorridos grupales con iluminación y seguridad para ciclistas.", Icons.Default.Nightlight),
        Triple("Asesoría Personalizada", "Expertos que te ayudan a elegir la bicicleta perfecta para ti.", Icons.Default.PersonSearch),
        Triple("Comunidad Ciclista", "Conéctate con otros entusiastas del ciclismo en tu área.", Icons.Default.Group)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 20.dp,
                end = 20.dp,
                top = paddingValues.calculateTopPadding() + 28.dp,
                bottom = paddingValues.calculateBottomPadding() + 56.dp
            ),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Header como item de la grilla (que ocupa 2 columnas)
            item(span = { GridItemSpan(2) }) {
                Header()
                Spacer(Modifier.height(24.dp))
            }

            // Tarjetas de servicios
            items(services) { (title, description, icon) ->
                ServiceMiniCard(title, description, icon) {
                    navController.navigate("serviceDetail/${title}/${description}")
                }
            }
        }
    }
}

@Composable
private fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Consulta los servicios disponibles para que tu experiencia sea única.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                lineHeight = 24.sp,
                letterSpacing = 0.2.sp
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.92f)
        )
    }
}

@Composable
fun ServiceMiniCard(title: String, description: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.3.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f),
                lineHeight = 16.sp
            ),
            maxLines = 4
        )
    }
}