package com.example.explorador.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.ui.theme.*
import com.example.explorador.viewmodel.ExploradorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: ExploradorViewModel) {
    var esRegistro by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val nombreError by viewModel.nombreError.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()
    val mensajeSistema by viewModel.mensajeAuth.collectAsState()
    val esMensajeError by viewModel.esErrorAuth.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CENAMAD",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = AzulPrimario
        )
        Text(
            text = if (esRegistro) "Registro de investigador" else "Explorador de conocimiento",
            fontSize = 16.sp,
            color = GrisTexto,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (esRegistro) {
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    if (nombreError != null) viewModel.validarFormulario(esRegistro, nombre, email, password)
                },
                label = { Text("Nombre completo") },
                isError = nombreError != null,
                supportingText = { nombreError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AzulPrimario,
                    focusedLabelColor = AzulPrimario
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (emailError != null) viewModel.validarFormulario(esRegistro, nombre, email, password)
            },
            label = { Text("Correo electrónico") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulPrimario,
                focusedLabelColor = AzulPrimario
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (passwordError != null) viewModel.validarFormulario(esRegistro, nombre, email, password)
            },
            label = { Text("Contraseña") },
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Alternar visibilidad", tint = GrisTexto)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AzulPrimario,
                focusedLabelColor = AzulPrimario
            )
        )

        mensajeSistema?.let { mensaje ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensaje,
                color = if (esMensajeError) RojoError else VerdeExito,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (esRegistro) {
                    viewModel.registrarUsuario(nombre, email, password) {
                        esRegistro = false
                        password = ""
                    }
                } else {
                    viewModel.iniciarSesion(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (esRegistro) "Registrarse" else "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Blanco
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                esRegistro = !esRegistro
                viewModel.limpiarMensajesAuth()
            }
        ) {
            Text(
                text = if (esRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate aquí",
                color = AzulPrimario,
                fontSize = 14.sp
            )
        }
    }
}
