package com.orizzonter.app.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CreateAccountScreen(navController: NavController) {
    // Estados para los campos de entrada
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Contenedor principal con fondo de pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Onda decorativa en la parte superior
        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = true
        )

        // Onda decorativa en la parte inferior
        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = false
        )

        // Contenido central con padding
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Formulario de registro con bordes y fondo semitransparente
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(32.dp))

                // Campo: Nombre completo (placeholder visible pero no editable)
                InputField(
                    text = name,
                    placeholder = "Nombre completo",
                )

                Spacer(Modifier.height(16.dp))

                // Campo: Correo electrónico
                InputField(
                    text = email,
                    placeholder = "Correo electrónico",
                )

                Spacer(Modifier.height(16.dp))

                // Campo: Contraseña (con asteriscos simulados)
                InputField(
                    text = password,
                    placeholder = "Contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(32.dp))

                // Botón de "Registrarse"
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                        .border(
                            1.5.dp,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            // Acción de registro (aquí navega a "home")
                            navController.navigate("home")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Registrarse",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Enlace para usuarios que ya tienen cuenta
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(
                        "¿Ya tienes cuenta? Inicia sesión",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun InputField(
    text: String,
    placeholder: String,
    isPassword: Boolean = false
) {
    // Caja de estilo que simula un campo de entrada
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Muestra el texto ingresado o el placeholder
        Text(
            text = when {
                text.isEmpty() -> placeholder
                isPassword -> "*".repeat(text.length) // Oculta contraseña
                else -> text
            },
            color = if (text.isEmpty())
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            else
                MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
    }
}
