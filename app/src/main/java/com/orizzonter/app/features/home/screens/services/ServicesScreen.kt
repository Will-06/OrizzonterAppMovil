package com.orizzonter.app.features.home.screens.services

// Importación de componentes de UI y diseño
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@Composable
fun ServicesScreen() {
    // Estructura principal de la pantalla
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()), // Permite scroll vertical
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Header() // Título y descripción superior

            Spacer(Modifier.height(32.dp))

            ServiceList() // Lista de servicios disponibles

            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun Header() {
    // Encabezado con texto informativo
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Consulta los servicios disponibles para que tu experiencia sea única.",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                lineHeight = 22.sp
            ),
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth(0.85f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ServiceList() {
    // Lista de servicios con título, descripción e ícono
    val services = listOf(
        Triple("Taller de Bicis", "Repara y mantén tu bicicleta con los mejores expertos.", Icons.Default.Build),
        Triple("Rutas Guiadas", "Explora rutas seguras y emocionantes con acompañamiento profesional.", Icons.Default.Map),
        Triple("Alquiler de Bicicletas", "Encuentra bicicletas disponibles para cualquier tipo de aventura.", Icons.Default.DirectionsBike),
        Triple("Tiendas Especializadas", "Compra accesorios y equipo de calidad para tu ciclismo.", Icons.Default.Storefront),
        Triple("Consejos de Salud", "Información sobre nutrición, cuidado y bienestar para ciclistas.", Icons.Default.HealthAndSafety),
        Triple("Puntos de Hidratación", "Localiza estaciones para hidratarte durante tus rutas.", Icons.Default.LocalCafe)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // Espaciado entre tarjetas
    ) {
        services.forEach { (title, description, icon) ->
            ServiceMiniCard(title, description, icon) // Tarjeta de cada servicio
        }
    }
}

@Composable
fun ServiceMiniCard(title: String, description: String, icon: ImageVector) {
    // Tarjeta individual de servicio
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono del servicio
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        // Título y descripción del servicio
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    lineHeight = 18.sp
                ),
                maxLines = 3 // Limita la descripción a 3 líneas
            )
        }
    }
}
