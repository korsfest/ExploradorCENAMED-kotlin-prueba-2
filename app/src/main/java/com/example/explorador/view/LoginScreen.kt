package com.example.explorador.view

import android.util.Patterns
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.explorador.repository.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    var esRegistro by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var mensajeSistema by remember { mutableStateOf<String?>(null) }
    var esMensajeError by remember { mutableStateOf(false) }

    fun validateForm(): Boolean {
        var isValid = true

        if (esRegistro && nombre.isBlank()) {
            nombreError = "El nombre es obligatorio"
            isValid = false
        } else {
            nombreError = null
        }

        if (email.isBlank()) {
            emailError = "El correo es obligatorio"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "Formato de correo inválido"
            isValid = false
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "La contraseña es obligatoria"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Mínimo 6 caracteres"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

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
            color = Color(0xFF2563EB)
        )
        Text(
            text = if (esRegistro) "Registro de investigador" else "Explorador de conocimiento",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (esRegistro) {
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    mensajeSistema = null
                    if (nombreError != null) validateForm()
                },
                label = { Text("Nombre completo") },
                isError = nombreError != null,
                supportingText = { nombreError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2563EB),
                    focusedLabelColor = Color(0xFF2563EB)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                mensajeSistema = null
                if (emailError != null) validateForm()
            },
            label = { Text("Correo electrónico") },
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                focusedLabelColor = Color(0xFF2563EB)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                mensajeSistema = null
                if (passwordError != null) validateForm()
            },
            label = { Text("Contraseña") },
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Alternar visibilidad", tint = Color.Gray)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF2563EB),
                focusedLabelColor = Color(0xFF2563EB)
            )
        )

        mensajeSistema?.let { mensaje ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensaje,
                color = if (esMensajeError) Color(0xFFDC2626) else Color(0xFF16A34A),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (validateForm()) {
                    if (esRegistro) {
                        val exito = sessionManager.registrarUsuario(nombre, email, password)
                        if (exito) {
                            esMensajeError = false
                            mensajeSistema = "Registro guardado. Ya puedes iniciar sesión."
                            esRegistro = false
                            password = ""
                        } else {
                            esMensajeError = true
                            mensajeSistema = "Este correo ya está registrado"
                        }
                    } else {
                        if (sessionManager.validarCredenciales(email, password)) {
                            sessionManager.guardarSesionActiva(email)
                            onLoginSuccess(email.trim())
                        } else {
                            esMensajeError = true
                            mensajeSistema = "Credenciales incorrectas o usuario no registrado"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = if (esRegistro) "Registrarse" else "Iniciar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = {
                esRegistro = !esRegistro
                mensajeSistema = null
                nombreError = null
                emailError = null
                passwordError = null
            }
        ) {
            Text(
                text = if (esRegistro) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate aquí",
                color = Color(0xFF2563EB),
                fontSize = 14.sp
            )
        }
    }
}