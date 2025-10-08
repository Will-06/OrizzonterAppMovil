package com.orizzonter.app.features.auth

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.orizzonter.app.features.auth.viewmodels.AuthViewModel
import com.orizzonter.app.features.auth.viewmodels.AuthViewModelFactory

@Composable
fun CreateAccountScreen(navController: NavController) {
    // Estados para los campos de entrada
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context))
    val registerState by authViewModel.registerState.collectAsState()

    // Navegar a "home" cuando el registro sea exitoso
    LaunchedEffect(registerState) {
        if (registerState is AuthViewModel.AuthState.Success) {
            navController.navigate("home") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.TopCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = true
        )

        WaveShape(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            isTop = false
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
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
                Text(
                    text = "Crear cuenta",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(32.dp))

                // Ahora con onTextChange para que sea editable
                InputField(
                    text = name,
                    onTextChange = { name = it },
                    placeholder = "Nombre completo"
                )

                Spacer(Modifier.height(16.dp))

                InputField(
                    text = email,
                    onTextChange = { email = it },
                    placeholder = "Correo electrónico"
                )

                Spacer(Modifier.height(16.dp))

                InputField(
                    text = password,
                    onTextChange = { password = it },
                    placeholder = "Contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(16.dp))

                InputField(
                    text = confirmPassword,
                    onTextChange = { confirmPassword = it },
                    placeholder = "Confirmar contraseña",
                    isPassword = true
                )

                Spacer(Modifier.height(32.dp))

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
                        .clickable(enabled = registerState !is AuthViewModel.AuthState.Loading) {
                            if (name.isNotBlank() && email.isNotBlank() &&
                                password.isNotBlank() && confirmPassword.isNotBlank()) {
                                authViewModel.register(name, email, password, confirmPassword)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (registerState is AuthViewModel.AuthState.Loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Registrarse",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                if (registerState is AuthViewModel.AuthState.Error) {
                    Text(
                        text = (registerState as AuthViewModel.AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

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
    onTextChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
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
    )
}
