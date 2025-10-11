package com.orizzonter.app.features.home.screens.routes

import android.content.Context
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesScreen() {
    val context = LocalContext.current
    val cornerRadius = 24.dp
    val shape = RoundedCornerShape(cornerRadius)
    val onSurfaceAlpha = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground

    // Estado para el mapa
    var isMapInitialized by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }

    // Inicializar Mapbox de forma segura
    LaunchedEffect(Unit) {
        try {
            // En versiones recientes, Mapbox se inicializa automáticamente
            // con el meta-data del AndroidManifest.xml
            isMapInitialized = true
        } catch (e: Exception) {
            hasError = true
            e.printStackTrace()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)
    ) {
        // 🌍 MAPBOX MAP - Solo mostrar si está inicializado
        if (isMapInitialized && !hasError) {
            MapboxMap(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp)),
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        zoom(2.0)
                        center(Point.fromLngLat(-98.0, 39.5))
                        pitch(0.0)
                        bearing(0.0)
                    }
                }
            )
        } else {
            // Loading o fallback mientras se inicializa
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .clip(RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (hasError) {
                    Text("Error cargando el mapa", color = Color.Red)
                } else {
                    CircularProgressIndicator()
                }
            }
        }

        // El resto de tu código permanece igual...
        // 🔎 Campo de búsqueda
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            var searchQuery by remember { mutableStateOf("") }

            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Buscar una ruta...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    .border(1.dp, onSurfaceAlpha, shape),
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
