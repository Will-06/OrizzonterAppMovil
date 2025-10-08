package com.orizzonter.app.features.auth

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orizzonter.app.R
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory

// Composable que dibuja una forma de onda en la parte superior o inferior
@Composable
fun WaveShape(
    modifier: Modifier = Modifier,
    color: Color,
    isTop: Boolean = true
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val waveHeight = height * 0.6f

        val path = Path().apply {
            if (isTop) {
                moveTo(0f, height)
                cubicTo(
                    width * 0.25f, height - waveHeight,
                    width * 0.75f, height + waveHeight,
                    width, height
                )
                lineTo(width, 0f)
                lineTo(0f, 0f)
            } else {
                moveTo(0f, 0f)
                cubicTo(
                    width * 0.25f, waveHeight,
                    width * 0.75f, -waveHeight,
                    width, 0f
                )
                lineTo(width, height)
                lineTo(0f, height)
            }
            close()
        }

        drawPath(path, color, style = Fill)
    }
}

// Botón para login con redes sociales con icono y clickeable
@Composable
fun SocialLoginButton(iconResId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.05f))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(iconResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}

// Pantalla de inicio de sesión
@Composable
fun LoginScreen(navController: NavController) {
    // ViewModel de autenticación
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))

    // Estado para email y contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by authViewModel.loginState.collectAsState()

    // Observar estado de login exitoso
    LaunchedEffect(loginState) {
        if (loginState is AuthViewModel.AuthState.Success) {
            navController.navigate("home")
        }
    }

    // Contenedor principal con fondo
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

        // Caja central para los campos y botones
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Columna con estilo de fondo, bordes y padding
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
                    .padding(vertical = 32.dp, horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título de la pantalla
                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(32.dp))

                // Campo para email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Correo electrónico") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(Modifier.height(16.dp))

                // Campo para contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Contraseña") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(Modifier.height(32.dp))

                // Botón para iniciar sesión
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
                        .clickable(enabled = loginState !is AuthViewModel.AuthState.Loading) {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                authViewModel.login(email, password)
                            }
                        }, // Navega a home al hacer click
                    contentAlignment = Alignment.Center
                ) {
                    if (loginState is AuthViewModel.AuthState.Loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Iniciar sesión",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Mostrar errores
                if (loginState is AuthViewModel.AuthState.Error) {
                    Text(
                        text = (loginState as AuthViewModel.AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Texto botón para recuperar contraseña
                TextButton(onClick = { navController.navigate("forgot_password") }) {
                    Text(
                        "¿Olvidaste tu contraseña?",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Texto separador para métodos alternativos
                Text(
                    text = "O continúa con",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(16.dp))

                // Botones para login social (Google, Facebook, Twitter)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SocialLoginButton(R.drawable.google) { /* Acción login Google */ }
                    SocialLoginButton(R.drawable.facebook) { /* Acción login Facebook */ }
                    SocialLoginButton(R.drawable.twitter) { /* Acción login WhatsApp */ }
                }

                Spacer(Modifier.height(32.dp))

                // Texto para invitar a registrarse
                Text(
                    text = "¿No tienes cuenta?",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium
                )

                // Botón para ir a crear cuenta
                TextButton(onClick = { navController.navigate("create_account") }) {
                    Text(
                        "Regístrate aquí",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
