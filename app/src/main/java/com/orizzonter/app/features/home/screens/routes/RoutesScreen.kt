package com.orizzonter.app.features.home.screens.routes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.orizzonter.app.R

@Composable
fun RoutesScreen() {
    val context = LocalContext.current
    MapboxOptions.accessToken = context.getString(R.string.mapbox_access_token)

    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    val onSurfaceAlpha = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground

    var searchQuery by remember { mutableStateOf("") }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            center(Point.fromLngLat(-76.6062, 2.4426)) // Centro histórico de Popayán
            zoom(12.0) // Un poco más de zoom para ver mejor la ciudad
            pitch(0.0)
            bearing(0.0)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {
        // Mapa como fondo
        MapboxMap(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(32.dp)),
            mapViewportState = mapViewportState
        )

        // Barra de búsqueda superior
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 4.dp, end = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar una ruta...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    // <-- Cambia aquí el ancho máximo
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .border(1.dp, onSurfaceAlpha, shape)
                    .padding(horizontal = 4.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = onBackgroundColor.copy(alpha = 0.5f),
                    unfocusedPlaceholderColor = onBackgroundColor.copy(alpha = 0.5f),
                    focusedTextColor = onBackgroundColor,
                    unfocusedTextColor = onBackgroundColor
                ),
                shape = shape,
                singleLine = true
            )

        }
    }
}
