package com.orizzonter.app.features.home.screens.community

// Importación de componentes necesarios para la interfaz y diseño
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.orizzonter.app.R

@Composable
fun CommunityScreen() {
    // Lista de publicaciones simuladas
    val communityPosts = listOf(
        CommunityPost(
            user = "Andrés Velasco",
            avatarResId = R.drawable.avatar,
            text = "¿Alguien ha probado una de las nuevas rutas rurales que suben por Santa Rosa o Las Guacas? Vi que están marcadas como dificultad media 🔥",
            likes = 14,
            comments = 5,
            imageResId = R.drawable.popayan
        ),
        CommunityPost(
            user = "Laura Fernández",
            avatarResId = R.drawable.avatarmujer,
            text = "Sí, fui el domingo por el sector de Las Guacas. Tiene una subida fuerte al inicio, pero la vista de Popayán desde arriba vale totalmente la pena . Lleven agua 💧",
            likes = 11,
            comments = 2,
            imageResId = null
        ),

        CommunityPost(
            user = "Carlos Méndez",
            avatarResId = R.drawable.avatar,
            text = "Recomiendo revisar la presión de las llantas antes. Cerca al parque de La Paz hay una tienda que ajusta gratis. La encontré por Oizzonter 🚲🔧",
            likes = 9,
            comments = 1,
            imageResId = R.drawable.monserrate
        ),

        CommunityPost(
            user = "Andrés Velasco",
            avatarResId = R.drawable.avatar,
            text = "Gracias por los datos! ¿Qué tal si armamos un recorrido grupal para este sábado y probamos juntos esa ruta? 🚴‍♀️🌿",
            likes = 17,
            comments = 6,
            imageResId = null
        ),

        CommunityPost(
            user = "Laura Fernández",
            avatarResId = R.drawable.avatarmujer,
            text = "¡Me apunto! Puedo marcar la ruta en la app y compartir el punto de encuentro. ¿8:30am en la glorieta norte les sirve?",
            likes = 15,
            comments = 4,
            imageResId = null
        ),

        CommunityPost(
            user = "Orizzonter Team",
            avatarResId = R.drawable.logo_orizzonter,
            text = "¡Nos encanta ver cómo se conectan! Recuerden que pueden registrar su ruta, compartirla con otros y calificarla. ¡Exploren nuevos Horizontes! 💚",
            likes = 25,
            comments = 10,
            imageResId = R.drawable.logo_orizzonter
        )
    )

    // Estructura principal con botón flotante
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción para nueva publicación */ },
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier.padding(bottom = 100.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Publicar")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        // Lista vertical de publicaciones
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Mostrar cada publicación
            items(communityPosts) { post ->
                CommunityPostCard(post)
            }
        }
    }
}

// Modelo de datos para una publicación
data class CommunityPost(
    val user: String,
    val avatarResId: Int,
    val text: String,
    val likes: Int,
    val comments: Int,
    val imageResId: Int?
)

@Composable
fun CommunityPostCard(post: CommunityPost) {
    // Tarjeta de publicación
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                RoundedCornerShape(24.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            // Encabezado con avatar y usuario
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(post.avatarResId),
                    contentDescription = "Avatar de ${post.user}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        post.user,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    Text(
                        "Hace 2 horas",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    )
                }
            }

            // Imagen si existe
            post.imageResId?.let {
                Spacer(Modifier.height(16.dp))
                Image(
                    painter = painterResource(it),
                    contentDescription = "Imagen de publicación",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.height(16.dp))

            // Texto de la publicación
            Text(
                post.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )

            Spacer(Modifier.height(16.dp))

            // Iconos de likes y comentarios
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.FavoriteBorder,
                    contentDescription = "Me gusta",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(8.dp))
                Text("${post.likes}", color = MaterialTheme.colorScheme.onBackground)

                Spacer(Modifier.width(24.dp))

                Icon(
                    Icons.Default.ChatBubbleOutline,
                    contentDescription = "Comentarios",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(8.dp))
                Text("${post.comments}", color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}
